package com.vasanthlab.serialport.comm;

public enum CommPortError {
	
	ERROR_10000(10000,"Error: Port is currently in use"),
	ERROR_10001(10001,"Exception occured in com port readevent"),
	ERROR_10002(10002,"ComPort Receiver Listeners Exception"),
	ERROR_10003(10003,"Something went wrong in writing data to COMPORT");
	
	
	private int errorCode;
	private String errorDescription;
	
	private CommPortError(int errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	} 
	
	public static CommPortError fromString(int errorCode) {
		for (CommPortError error : CommPortError.values()) {
			if (error.getErrorCode()== errorCode) {
				return error;
			}
		}
		return null;
	}

}
