package com.vasantlab.tag.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasantlab.tag.processingservice.interfaces.TagReadProcessingService;

@Service("tagRead")
public class TagRead implements TagCommunicationStateInterface {
	@Autowired
	private TagReadProcessingService tagReadProcessingService;

	@Override
	public void doAction(String stateId) {
		tagReadProcessingService.tagDataRead();
	}

}
