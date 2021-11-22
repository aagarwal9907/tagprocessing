package com.vasantlab.tag.processingservice.interfaces;

import com.vasantlab.util.ErrorCommunicationState;

public interface TagErrorProcessingService {

	public void tagErrorProcessing(String errorCode);
	
	public void propagateError(ErrorCommunicationState state);
}
