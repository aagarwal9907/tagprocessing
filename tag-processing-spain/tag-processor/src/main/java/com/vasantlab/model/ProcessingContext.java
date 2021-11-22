package com.vasantlab.model;

import java.util.Map;

import com.vasantlab.tag.model.SargasDriver;
import com.vasantlab.tag.service.exception.TagStatus;


public class ProcessingContext {

	private SargasDriver sgDriver;
	private TagStatus tagError;
	private Map<String,String> status;

	public Map<String, String> getStatus() {
		return status;
	}

	public void setStatus(Map<String, String> status) {
		this.status = status;
	}

	public SargasDriver getSgDriver() {
		return sgDriver;
	}

	public void setSgDriver(SargasDriver sgDriver) {
		this.sgDriver = sgDriver;
	}

	public TagStatus getTagError() {
		return tagError;
	}

	public void setTagError(TagStatus tagError) {
		this.tagError = tagError;
	}

}
