package com.vasantlab.tag.processingservice;

import static com.vasantlab.util.Constants.RFID_DATA;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;
import com.vasanthlab.dao.data.service.interfaces.RFIDDataService;
import com.vasanthlab.serialport.comm.CommPortEvent;
import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.model.DataProcessingStatus;
import com.vasantlab.model.DeviceError;
import com.vasantlab.model.ProcessingContext;
import com.vasantlab.model.TagInfo;
import com.vasantlab.tag.processingservice.interfaces.TagReadProcessingService;
import com.vasantlab.tag.service.exception.TagStatus;
import com.vasantlab.tagdriver.RFIdDriverReference;
import com.vasantlab.tagdriver.RFIdInterfaceAdaptor;
import com.vasantlab.util.CachingUtil;
import com.vasantlab.util.CommPortCommandUtil;
import com.vasantlab.util.ErrorCommunicationState;
import com.vasantlab.util.TagValidation;

@Component
public class TagReadProcessingServiceImpl implements TagReadProcessingService, ApplicationEventPublisherAware {
    private static Logger log = LoggerFactory.getLogger(TagReadProcessingServiceImpl.class);
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private TagValidation tagOperationService;
    @Autowired
    private RFIDDataService rfidDataService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RFIdDriverReference rfIdDriverReference;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
	this.publisher = publisher;
    }

    @Override
    public void tagDataRead() {
	Stopwatch watchTime = Stopwatch.createStarted();

	RFIdInterfaceAdaptor rfIdAdaptor = rfIdDriverReference.getDriverReference();
	ProcessingContext readProcessingContext = rfIdAdaptor.tagRead();

	RFIDData rfidData = CachingUtil.cachingProcessRFIDData.get("WritenData");

	if (rfidData.getStatus() == null) {
	    TagInfo tagInfo = tagOperationService.epcValidation(readProcessingContext, rfidData.getEpc(),
		    rfidData.getAccess_Password(), rfidData.getKill_Password(), rfidData.getUser_Memory());
	    TagStatus validation = tagInfo.getTagStatus();
	    StringBuilder memory_rw_error = new StringBuilder();
	    if (validation == TagStatus.VALIDATIONOK) {
		rfidData.setStatus(DataProcessingStatus.DO.toString());
		rfidData.setCount(String.valueOf(tagInfo.getReadCount()));
		rfidData.setRssi(String.valueOf(tagInfo.getRssi()));
		rfidData.setTime(String.valueOf(tagInfo.getTimeStamp()));
		rfidData.setTidData(tagInfo.getTidData());

		Map<String, String> memoryError = readProcessingContext.getStatus();
		Set<Entry<String, String>> set = memoryError.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
		while (iterator.hasNext()) {
		    Entry<String, String> error = iterator.next();
		    memory_rw_error.append(error.getKey() + "=" + error.getValue() + ".");
		}
		rfidData.setMemory_rw_error(memory_rw_error.toString());

		rfidDataService.saveRFIDData(rfidData);
		messageService.sendMessage(rfidData);
		log.info("We are able to read all data from Tag for: {}", rfidData.getEpc());
		CachingUtil.cachingProcessRFIDData.clear();
		CachingUtil.cachingRFIdInterfaceAdaptor.clear();
		if (CachingUtil.cachingRFIDDatalist.get(RFID_DATA).isEmpty()) {
		    CachingUtil.clearCachingData();
		    ErrorCommunicationState state = ErrorCommunicationState.fromString("0000");
		    log.info(state.getErrorState());
		    DeviceError error = new DeviceError(state.getErrorState(), state);
		    DeviceError.push(error);
		}
		watchTime.stop();
		log.info("Time spend on reading Tag and building cache {}", watchTime.elapsed());
		publisher.publishEvent(new CommPortEvent(CommPortCommandUtil.READOKCOMMAND));
	    } else if (validation == TagStatus.VALIDATIONFAIL) {

		rfidData.setStatus(DataProcessingStatus.FA.toString());

		rfidDataService.saveRFIDData(rfidData);
		messageService.sendMessage(rfidData);
		log.info("We are not able to read all data from Tag for {}  So tag mark as Fail", rfidData.getEpc());
		CachingUtil.cachingProcessRFIDData.clear();
		CachingUtil.cachingRFIdInterfaceAdaptor.clear();
		if (CachingUtil.cachingRFIDDatalist.get(RFID_DATA).isEmpty()) {
		    CachingUtil.clearCachingData();
		    ErrorCommunicationState state = ErrorCommunicationState.fromString("0000");
		    log.info(state.getErrorState());
		    DeviceError error = new DeviceError(state.getErrorState(), state);
		    DeviceError.push(error);
		}
		watchTime.stop();
		log.info("Time spend on reading Tag and building cache {}", watchTime.elapsed());
		publisher.publishEvent(new CommPortEvent(CommPortCommandUtil.READEFAILCOMMAND));
	    }

	} else {
	    rfidData.setDate(java.time.LocalDate.now().toString());
	    rfidDataService.saveRFIDData(rfidData);
	    messageService.sendMessage(rfidData);
	    CachingUtil.cachingProcessRFIDData.clear();
	    CachingUtil.cachingRFIdInterfaceAdaptor.clear();
	    CachingUtil.cachingReceiveByte.clear();
	    watchTime.stop();
	    log.info("Time spend on reading Tag and building cache {}", watchTime.elapsed());
	    publisher.publishEvent(new CommPortEvent(CommPortCommandUtil.READEFAILCOMMAND));
	}

    }

}
