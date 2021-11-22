package com.vasantlab.model;

public class ProcessControllerResponse {
	
	private String message;
	public ProcessControllerResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "ProcessControllerResponse [message=" + message + "]";
	}
	

}
