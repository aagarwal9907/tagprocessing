package com.vasantlab.tag.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vasantlab.tag.processingservice.interfaces.TagErrorProcessingService;
import com.vasantlab.util.CachingUtil;


@Service("tagError")
public class TagError implements TagCommunicationStateInterface {
	@Autowired
	private TagErrorProcessingService tagErrorProcessingService;
	
	@Override
	public void doAction(String stateId) {
		char[] data = CachingUtil.cachingReceiveByte.get("receiveByte");
		StringBuilder opCode = new StringBuilder();
		opCode.append(data[10]).append(data[11]).append(data[12]).append(data[13]);
		String errorCode=opCode.toString();
		tagErrorProcessingService.tagErrorProcessing(errorCode);

	}

}
