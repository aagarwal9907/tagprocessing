package com.vasantlab.tag.processingservice;

import static com.vasantlab.util.Constants.BARCODE;
import static com.vasantlab.util.Constants.ERROR_CODE;
import static com.vasantlab.util.Constants.FEED_DATA;
import static com.vasantlab.util.Constants.ORDER_ID;
import static com.vasantlab.util.Constants.RFID_DATA;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.google.common.base.Stopwatch;
import com.vasanthlab.dao.data.service.interfaces.RFIDDataService;
import com.vasanthlab.dao.data.service.interfaces.TagDataFeedService;
import com.vasanthlab.serialport.comm.CommPortEvent;
import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.data.tables.TagDataFeed;
import com.vasantlab.exception.FileProcessingException;
import com.vasantlab.file.process.RRIDFileProcessImpl;
import com.vasantlab.file.processinterface.OrderIdProcessFile;
import com.vasantlab.model.TagProcessingInput;
import com.vasantlab.tag.processingservice.exception.TagProcessingException;
import com.vasantlab.tag.processingservice.interfaces.TagStartService;
import com.vasantlab.util.CachingUtil;
import com.vasantlab.util.CommPortCommandUtil;

/**
 * This service class handles the request to start tagging by getting input from
 * the load file
 * 
 * @author Shubha
 *
 */
@Service("startService")
public class TagStartServiceImpl implements TagStartService, ApplicationEventPublisherAware {

    private static Logger log = LoggerFactory.getLogger(TagStartServiceImpl.class);

    private ApplicationEventPublisher publisher;

    @Autowired
    TagDataFeedService tagDataFeedService;
    @Autowired
    RRIDFileProcessImpl rfidProcessFile;
    @Autowired
    OrderIdProcessFile orderIdProcessFile;
    @Autowired
    private RFIDDataService rfidDataService;

    @Override
    public void startProcessing(TagProcessingInput tagProcessingInput)
	    throws TagProcessingException, FileProcessingException {
	Stopwatch watchTime = Stopwatch.createStarted();
	String orderId = tagProcessingInput.getOrderId();
	String barcode = tagProcessingInput.getBarcode();
	if (StringUtils.isEmpty(CachingUtil.cachingErrorCode.get(ERROR_CODE))) {
	    // This condition executed very first time for every new order and barcode.
	    // we are reading both file which is required for tag write and read process.
	    // and we are putting data into memory.
	    int dataCount = tagDataFeedService.findBarcode(orderId, barcode);

	    if (dataCount == 0 && (CachingUtil.cachingFeedData.get(FEED_DATA) == null)
		    && (CachingUtil.cachingRFIDDatalist.get(RFID_DATA) == null)) {

		readFilesData(orderId, barcode);

	    } else if (dataCount != 0 && (CachingUtil.cachingFeedData.get(FEED_DATA) == null)
		    && (CachingUtil.cachingRFIDDatalist.get(RFID_DATA) == null)) {
		// this condition executed with any kind of Failure where we lost memory data.

		readDataWithFileAndDB(orderId, barcode);

	    } else if (dataCount != 0 && (CachingUtil.cachingFeedData.get(FEED_DATA) != null)
		    && (CachingUtil.cachingRFIDDatalist.get(RFID_DATA) != null)) {
		CachingUtil.cachingReceiveByte.clear();
		publisher.publishEvent(new CommPortEvent(CommPortCommandUtil.STARTOKCOMMAND));
	    }
	} else if ((CachingUtil.cachingFeedData.get(FEED_DATA) != null)
		&& (CachingUtil.cachingRFIDDatalist.get(RFID_DATA) != null)) {
	    // this condition executed when system design error code is coming

	    CachingUtil.cachingReceiveByte.clear();
	    watchTime.stop();
	    log.info("Time spend on reading file and building cache {}", watchTime.elapsed());
	    publisher.publishEvent(new CommPortEvent(CommPortCommandUtil.STARTOKCOMMAND));
	}

    }

    private void readDataWithFileAndDB(String orderId, String barcode)
	    throws TagProcessingException, FileProcessingException {
	CachingUtil.caching.set(ORDER_ID, orderId);
	CachingUtil.caching.set(BARCODE, barcode);

	TagDataFeed feedData = tagDataFeedService.findTagData(orderId, barcode);

	List<RFIDData> tagRfidData = rfidDataService.finddata(orderId, barcode);
	List<RFIDData> rfidData = rfidProcessFile.getRFIDProcessFile(feedData);
	if (tagRfidData.size() != rfidData.size()) {
	    if (tagRfidData.isEmpty()) {
		CachingUtil.cachingFeedData.set(FEED_DATA, feedData);
		CachingUtil.cachingRFIDDatalist.set(RFID_DATA, rfidData);
		log.info("Put RFID Data into memory");
		CachingUtil.cachingReceiveByte.clear();
		publisher.publishEvent(new CommPortEvent(CommPortCommandUtil.STARTOKCOMMAND));
	    } else {

		rfidData = rfidData.stream()
			.filter(rfid -> tagRfidData.stream()
				.noneMatch(tag -> rfid.getEpc().equalsIgnoreCase(tag.getEpc())
					&& rfid.getOrderId().equalsIgnoreCase(tag.getOrderId())
					&& rfid.getBarcode().equalsIgnoreCase(tag.getBarcode())))
			.collect(Collectors.toList());

		CachingUtil.cachingFeedData.set(FEED_DATA, feedData);
		CachingUtil.cachingRFIDDatalist.set(RFID_DATA, rfidData);
		log.info("Put RFID Data into memory");
		CachingUtil.cachingReceiveByte.clear();
		publisher.publishEvent(new CommPortEvent(CommPortCommandUtil.STARTOKCOMMAND));
	    }
	} else {
	    log.error("this order id and barcode already used for processing");
	    throw new TagProcessingException(
		    String.format("This orderId: %s and barcode: %s already used for processing", orderId, barcode));
	}

    }

    private void readFilesData(String orderId, String barcode) throws TagProcessingException, FileProcessingException {
	CachingUtil.caching.set(ORDER_ID, orderId);
	CachingUtil.caching.set(BARCODE, barcode);

	List<TagDataFeed> tagData = null;
	try {
	    tagData = orderIdProcessFile.getProcessFile(orderId);
	} catch (FileNotFoundException e) {
	    throw new TagProcessingException(e.getLocalizedMessage());
	}

	String filePath = findFilePath(tagData);

	if (!StringUtils.isEmpty(filePath)) {
	    tagDataFeedService.saveFileRecords(CachingUtil.cachingFeedData.get(FEED_DATA));
	    log.info("Save data from first file for {} and {}", orderId, barcode);
	    List<RFIDData> rfidData = rfidProcessFile.getRFIDProcessFile(CachingUtil.cachingFeedData.get(FEED_DATA));
	    CachingUtil.cachingRFIDDatalist.set(RFID_DATA, rfidData);
	    log.info("Put RFID Data into memory");
	    CachingUtil.cachingReceiveByte.clear();
	    publisher.publishEvent(new CommPortEvent(CommPortCommandUtil.STARTOKCOMMAND));
	} else {
	    log.error("For the given OrderId and Barcode not able to find FilePath !!");
	    throw new TagProcessingException("For the given OrderId and Barcode not able to find FilePath");
	}

    }

    private String findFilePath(List<TagDataFeed> tagData) {
	String filePath = null;
	Iterator<TagDataFeed> iterator = tagData.iterator();
	while (iterator.hasNext()) {
	    TagDataFeed tagDataFeed = iterator.next();
	    if (tagDataFeed.getIOS_NO().equals(CachingUtil.caching.get(ORDER_ID))
		    && tagDataFeed.getBarcode_details().equals(CachingUtil.caching.get(BARCODE))) {
		filePath = tagDataFeed.getFile_path();
		CachingUtil.cachingFeedData.set(FEED_DATA, tagDataFeed);
		log.info("Put data into memory");
	    }
	}
	return filePath;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
	this.publisher = publisher;
    }

}
