package com.vasantlab.tag.processingservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.vasanthlab.dao.data.service.interfaces.RFIDErrorLogDataService;
import com.vasanthlab.serialport.comm.CommPortEvent;
import com.vasantlab.data.tables.RFIDErrorLog;
import com.vasantlab.model.DeviceError;
import com.vasantlab.tag.processingservice.interfaces.TagErrorProcessingService;
import com.vasantlab.util.CachingUtil;
import com.vasantlab.util.ErrorCommunicationState;

@Service("tagErrorProcessingService")
public class TagErrorProcessingServiceImpl implements TagErrorProcessingService, ApplicationEventPublisherAware {

	private static Logger log = LoggerFactory.getLogger(TagErrorProcessingServiceImpl.class);

	private ApplicationEventPublisher publisher;
	@Autowired
	private RFIDErrorLogDataService rfidErrorLogDataService;
	
	@Override
	public void tagErrorProcessing(String errorCode) {

		CachingUtil.cachingErrorCode.set("ErrorCode", errorCode);
		ErrorCommunicationState state = ErrorCommunicationState.fromString(errorCode);
		log.info(state.getErrorState());
		DeviceError error = new DeviceError(state.getErrorState(), state);
		DeviceError.push(error);
		RFIDErrorLog rfidErrorLog = new RFIDErrorLog();
		rfidErrorLog.setOrder_id(CachingUtil.caching.get("orderId"));
		rfidErrorLog.setBar_code(CachingUtil.caching.get("barcode"));
		rfidErrorLog.setError_code(errorCode);
		rfidErrorLog.setMsg(state.getErrorState());
		rfidErrorLog.setDate(java.time.LocalDate.now().toString());
		rfidErrorLog.setTime(java.time.LocalTime.now().toString());

		rfidErrorLogDataService.saveRFIDErrorLogData(rfidErrorLog);

	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
	
	@Override
	public void propagateError(ErrorCommunicationState state) {
		if(state.getCommandByte()!=null) {
			publisher.publishEvent(new CommPortEvent(state.getCommandByte()));
		}else {
			log.info("Error Cannot be Propagated {} no command defined",state);
			
		}
	}

}
