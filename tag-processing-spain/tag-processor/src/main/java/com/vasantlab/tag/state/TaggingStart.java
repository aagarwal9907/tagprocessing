package com.vasantlab.tag.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasantlab.exception.FileProcessingException;
import com.vasantlab.model.DeviceReadyEvent;
import com.vasantlab.model.TagProcessingInput;
import com.vasantlab.tag.processingservice.exception.TagProcessingException;
import com.vasantlab.tag.processingservice.interfaces.TagStartService;
import com.vasantlab.util.CachingUtil;

import io.micrometer.core.instrument.util.StringUtils;

@Service("taggingStart")
public class TaggingStart implements TagCommunicationStateInterface {
	private static Logger log = LoggerFactory.getLogger(TaggingStart.class);
	@Autowired
	private TagStartService startService;

	@Override
	public void doAction(String stateId) {
		// enable button in gui for processing and we get tagProcessingInput
		// object of tagProcessingInput just created for testing
		log.info("received status {}", stateId);
		DeviceReadyEvent.setDeviceReady(true);
		log.info("Device Ready Status {}", DeviceReadyEvent.isDeviceReady());
		String orderId = CachingUtil.caching.get("orderId");
		String barcode = CachingUtil.caching.get("barcode");
		if (StringUtils.isNotEmpty(barcode) && StringUtils.isNotEmpty(orderId)) {
			TagProcessingInput tagProcessingInput = new TagProcessingInput();
			tagProcessingInput.setBarcode(barcode);
			tagProcessingInput.setOrderId(orderId);
			// conditionally enable below code.
			try {
				startService.startProcessing(tagProcessingInput);
			} catch (TagProcessingException e) {
				log.error("Error in TaggingStart.",e);
			}catch (FileProcessingException e) {
				log.error("Error in TaggingStart.",e);
			}
		}

	}

}
