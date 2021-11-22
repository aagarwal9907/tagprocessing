package com.vasantlab.tag.service.exception;



public enum TagDriverError {
	
	ERROR_20000(20000,"No Antenna provided at the time of initialition"),
	ERROR_20001(20001,"Driver Connection error"),
	ERROR_20002(20002,"Exception while reading All EPC Data"),
	ERROR_20003(20003,"Exception while reading KillPassWord"),
	ERROR_20004(20004,"Exception while reading AccessPassWord"),
	ERROR_20005(20005,"Reader does not support any regions"),
	ERROR_20006(20006,"TagDataEvent object is not created at the time of write"),
	ERROR_20007(20007,"Not able to write EPC Data"),
	ERROR_20008(20008,"Not able to write Kill Password"),
	ERROR_20009(20009,"Not able to write Access Password"),
	ERROR_20010(20010,"Not able to write and Read UserMemory"),
	ERROR_20011(20011,"Exception at the time of LockMemory"),
	ERROR_20012(20012,"Module doesn't has antenna detection support, please provide antenna list"),
	ERROR_20013(20013,"Exception while writing tag in SargasDriverWrite class"),
	ERROR_20014(20014,"Exception while reading tag in SargasDriverRead class"),
	ERROR_20015(20015,"Not able to Read TagDatd"),
    	ERROR_20016(20016,"Not able to Read TID Data");
	private int errorCode;
	private String errorDescription;
	
	public int getErrorCode() {
		return errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	private TagDriverError(int errorCode, String errorDescription) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}
	public static TagDriverError fromString(int errorCode) {
		for (TagDriverError error : TagDriverError.values()) {
			if (error.getErrorCode()== errorCode) {
				return error;
			}
		}
		return null;
	}
	

}
