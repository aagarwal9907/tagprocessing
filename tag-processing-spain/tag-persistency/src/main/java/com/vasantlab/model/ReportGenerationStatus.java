package com.vasantlab.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This is response object for giving out status on the user interface when triggered with order id and barcode for generation of report
 * The default object is generating success response.
 * @author Shubha
 * 
 */
public class ReportGenerationStatus {

	private String message;
	private ErrorCode errorCode;

	public static  ReportGenerationStatus createNoRecordStatus() {
		ReportGenerationStatus status=new ReportGenerationStatus();
		status.setErrorCode(ErrorCode.ERROR);
		status.setMessage("Requested OrderId and Barcode was not found in system");
		return status;
	}
	
	/**
	 * The default report generation status is set as success
	 */
	public ReportGenerationStatus() {
		errorCode=ErrorCode.SUCCESS;
		message="Report generated on filesystem successfully";
	}

	public ReportGenerationStatus(String message, ErrorCode errorCode) {

		this.message = message;
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	@Override
	public String toString() {
		 
		 return ToStringBuilder.reflectionToString(this);
	}
}
