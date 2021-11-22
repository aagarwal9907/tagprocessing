package com.vasantlab.tag.processingservice;

import static com.vasantlab.util.Constants.ADAPTOR;
import static com.vasantlab.util.Constants.FEED_DATA;
import static com.vasantlab.util.Constants.ORDER_ID;
import static com.vasantlab.util.Constants.RECEIVE_BYTE;
import static com.vasantlab.util.Constants.RFID_DATA;
import static com.vasantlab.util.Constants.WRITEN_DATA;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.vasanthlab.serialport.comm.CommPortEvent;
import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.model.DataProcessingStatus;
import com.vasantlab.model.ProcessingContext;
import com.vasantlab.tag.processingservice.interfaces.TagWriteProcessingService;
import com.vasantlab.tag.service.exception.TagStatus;
import com.vasantlab.tagdriver.RFIdDriverReference;
import com.vasantlab.tagdriver.RFIdInterfaceAdaptor;
import com.vasantlab.util.CachingUtil;
import com.vasantlab.util.CommPortCommandUtil;

@Component
public class TagWriteProcessingServiceImpl implements TagWriteProcessingService, ApplicationEventPublisherAware {
    private static Logger log = LoggerFactory.getLogger(TagWriteProcessingServiceImpl.class);
    private ApplicationEventPublisher publisher;

    @Autowired
    private RFIdDriverReference rfIdDriverReference;

    private RFIdInterfaceAdaptor rfIdAdaptor;

    private ProcessingContext writeProcessingContext;

    @Override
    public void tagDataWrite(String orderId, String barcode) {
	StopWatch watchTime = new StopWatch();
	watchTime.start();

	List<RFIDData> rfidData = CachingUtil.cachingRFIDDatalist.get(RFID_DATA);
	rfIdAdaptor = rfIdDriverReference.getDriverReference();

	if ((CachingUtil.cachingProcessRFIDData.get(WRITEN_DATA) != null) && (Arrays
		.toString(CachingUtil.cachingReceiveByte.get(RECEIVE_BYTE)).equals(CommPortCommandUtil.WRITEREPEAT))) {
	    log.info("for EPC: {} Repeat the write Command.",
		    CachingUtil.cachingProcessRFIDData.get(WRITEN_DATA).getEpc());

	    writeProcessingContext = rfIdAdaptor.tagWrite(CachingUtil.cachingProcessRFIDData.get(WRITEN_DATA),
		    CachingUtil.cachingFeedData.get(FEED_DATA));
	    verifiyWriteData(CachingUtil.cachingProcessRFIDData.get(WRITEN_DATA));
	    CachingUtil.cachingReceiveByte.clear();
	    watchTime.stop();
	    log.info("Time spend on writting Tag and building cache {}", watchTime.shortSummary());
	    publisher.publishEvent(new CommPortEvent(CommPortCommandUtil.WRITEOKCOMMAND));
	} else if (rfidData != null && !rfidData.isEmpty()) {

	    for (RFIDData data : rfidData) {
		writeProcessingContext = rfIdAdaptor.tagWrite(data, CachingUtil.cachingFeedData.get(FEED_DATA));
		verifiyWriteData(data);
		publisher.publishEvent(new CommPortEvent(CommPortCommandUtil.WRITEOKCOMMAND));
	    }
	    watchTime.stop();
	    log.info("Time spend on writting Tag and building cache {}", watchTime.shortSummary());
	}
    }

    private void verifiyWriteData(RFIDData data) {
	if (writeProcessingContext.getTagError() == TagStatus.WRITEOK) {
	    CachingUtil.cachingRFIdInterfaceAdaptor.set(ADAPTOR, rfIdAdaptor);
	    data.setOrderId(CachingUtil.caching.get(ORDER_ID));
	    CachingUtil.cachingProcessRFIDData.set(WRITEN_DATA, data);
	    CachingUtil.cachingRFIDDatalist.get(RFID_DATA).remove(data);
	    log.info("We are able to write all data for {}", data.getEpc());

	} else if (writeProcessingContext.getTagError() == TagStatus.WRITEFAIL) {
	    // exception
	    CachingUtil.cachingRFIdInterfaceAdaptor.set(ADAPTOR, rfIdAdaptor);
	    data.setStatus(DataProcessingStatus.FA.toString());
	    data.setOrderId(CachingUtil.caching.get(ORDER_ID));
	    CachingUtil.cachingProcessRFIDData.set(WRITEN_DATA, data);
	    CachingUtil.cachingRFIDDatalist.get(RFID_DATA).remove(data);
	    log.info("We are not able to write all data for {}", data.getEpc());
	}
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
	this.publisher = publisher;
    }

}
