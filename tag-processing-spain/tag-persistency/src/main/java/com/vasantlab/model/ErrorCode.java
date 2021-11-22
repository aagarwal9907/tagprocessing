package com.vasantlab.model;

public enum ErrorCode {

	SUCCESS("Success"),ERROR("Error");
	
	private String value;
	
	ErrorCode(String value) {
		this.value=value;
	}

	public String getValue() {
		return value;
	}

}
