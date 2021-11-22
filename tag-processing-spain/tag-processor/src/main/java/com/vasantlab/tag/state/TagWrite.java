package com.vasantlab.tag.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vasantlab.tag.processingservice.interfaces.TagWriteProcessingService;
import com.vasantlab.util.CachingUtil;

@Service("tagWrite")
public class TagWrite implements TagCommunicationStateInterface {
	@Autowired
	private TagWriteProcessingService tagWriteProcessingService;

	@Override
	public void doAction(String stateId) {
		String orderId =CachingUtil.caching.get("orderId");
		String barcode =CachingUtil.caching.get("barcode");
		tagWriteProcessingService.tagDataWrite(orderId,barcode);
	}

}
