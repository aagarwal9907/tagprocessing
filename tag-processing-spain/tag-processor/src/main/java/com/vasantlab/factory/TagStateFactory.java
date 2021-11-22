package com.vasantlab.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vasantlab.tag.state.TagCommunicationStateInterface;

import com.vasantlab.util.CommunicationState;

@Component("tagStateFactory")
public class TagStateFactory {

	@Autowired
	private TagCommunicationStateInterface taggingStart;

	@Autowired
	private TagCommunicationStateInterface tagWrite;

	@Autowired
	private TagCommunicationStateInterface tagRead;

	@Autowired
	private TagCommunicationStateInterface readConfigService;
	
	@Autowired
	private TagCommunicationStateInterface tagError;

	public TagCommunicationStateInterface getStateObject(CommunicationState communicationState) {
		if (communicationState.equals(CommunicationState.START)) {
			return taggingStart;
		}  else if (communicationState.equals(CommunicationState.WRITESTART)) {
			return tagWrite;
		} else if (communicationState.equals(CommunicationState.READSTART)) {
			return tagRead;
		} else if (communicationState.equals(CommunicationState.ERRORCODE)) {
			return tagError;
		}else if (communicationState.name().contains("CONFIG")){
			return readConfigService;
		}
		return null;
	}
}
