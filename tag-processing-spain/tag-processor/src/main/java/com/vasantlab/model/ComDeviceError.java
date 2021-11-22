package com.vasantlab.model;

public class ComDeviceError {
	
	private String errorCode;
	private String message;
		
	public ComDeviceError() {
	
	}
	public ComDeviceError(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "ComDeviceError [errorCode=" + errorCode + ", message=" + message + "]";
	}
	


}
