package com.vasantlab.tag.processingservice.interfaces;

import com.vasantlab.exception.FileProcessingException;
import com.vasantlab.model.TagProcessingInput;
import com.vasantlab.tag.processingservice.exception.TagProcessingException;

public interface TagStartService {
	public void startProcessing(TagProcessingInput tagProcessingInput)throws TagProcessingException,FileProcessingException;
}
