package com.vasantlab.tag.processingservice.exception;

public class TagProcessingException extends Exception {


	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public TagProcessingException(){
		super();
	}
	
	public TagProcessingException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}