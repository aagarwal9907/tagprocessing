package com.vasantlab.tag.service.exception;

public enum TagStatus {

	READFAIL("5001"), READOK("2001"), WRITEFAIL("5002"), WRITEOK("2002"), VALIDATIONFAIL("5003"), VALIDATIONOK("2003");

	private String errorCode;

	private TagStatus(String errorCode) {
		this.errorCode = errorCode;

	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public static TagStatus fromString(String text) {
		for (TagStatus err : TagStatus.values()) {
			if (err.errorCode.equalsIgnoreCase(text)) {
				return err;
			}
		}
		return null;
	}
}
