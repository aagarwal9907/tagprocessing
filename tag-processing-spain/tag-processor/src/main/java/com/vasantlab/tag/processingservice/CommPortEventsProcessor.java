package com.vasantlab.tag.processingservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import static com.vasantlab.util.Constants.*;
import com.vasanthlab.serialport.comm.CommPortEvent;
import com.vasantlab.tag.state.TagContext;
import com.vasantlab.util.CachingUtil;
import com.vasantlab.util.CommPortCommandUtil;
import com.vasantlab.util.ErrorCommunicationState;

/**
 * Once the data is available on the COMPORT as input, this event processor would be triggered to handle 
 * transition of flow.
 * The <code>onApplicationEvent</code> would be triggered with given <code>CommPortEvent</code> object 
 * which will provide data received in <code>char[]</code>
 * 
 **/
@Component
public class CommPortEventsProcessor implements ApplicationListener<CommPortEvent> {
	private static Logger log = LoggerFactory.getLogger(CommPortEventsProcessor.class);
	@Autowired
	private TagContext tagContext;

	@Override
	public void onApplicationEvent(CommPortEvent event) {
		if (event.getData() != null) {
			
			CommPortEvent commPortEvent = event;
			char[] receive = commPortEvent.getData();
			String recevieData = String.valueOf(receive);
			
			if (CachingUtil.cachingErrorCode.get(ERROR_CODE) != null) {
				String errorCode = CachingUtil.cachingErrorCode.get(ERROR_CODE);
				ErrorCommunicationState state=ErrorCommunicationState.fromString(errorCode);
				String error = state.getErrorCode();
				boolean result = CommPortCommandUtil.getCommand(error, recevieData.toUpperCase());
				if(result){
					log.info("Byte Received {}" , String.valueOf(recevieData.toCharArray()));
					CachingUtil.cachingReceiveByte.set(RECEIVE_BYTE, recevieData.toCharArray());
					tagContext.processCommPortData(recevieData.toCharArray());
				}	
			}else{
				log.info("Byte Received {}" , String.valueOf(recevieData.toCharArray()));
				CachingUtil.cachingReceiveByte.set(RECEIVE_BYTE, recevieData.toCharArray());
				tagContext.processCommPortData(recevieData.toCharArray());
			}
			
		}
		if (event.getData() == null) {
			log.info("Command not Received waiting for command.");
		}
	}
}
