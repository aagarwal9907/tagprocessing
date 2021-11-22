package com.vasanthlab.serialport.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComPortErrorHandler {
	private static Logger log = LoggerFactory.getLogger(ComPortErrorHandler.class);
	
	public ComPortErrorHandler() {
		super();
		}

	public void getComPortError(int errorCode,StackTraceElement[] stackTrace){
		CommPortError commPortError = CommPortError.fromString(errorCode);
		StringBuilder error = new StringBuilder();
		error.append(String.valueOf(commPortError.getErrorCode())).append("::").append(commPortError.getErrorDescription()).append("==>").append(stackTrace);
		log.error(error.toString());
	}
	
	
}
