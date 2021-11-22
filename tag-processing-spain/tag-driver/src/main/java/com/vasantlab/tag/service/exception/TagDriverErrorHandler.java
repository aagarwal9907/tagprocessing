package com.vasantlab.tag.service.exception;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TagDriverErrorHandler {
	private static Logger log = LoggerFactory.getLogger(TagDriverErrorHandler.class);

	public TagDriverErrorHandler() {
		super();
	}

	public void getTagDriverError(int errorCode, StackTraceElement[] stackTrace) {
		TagDriverError tagDriverError = TagDriverError.fromString(errorCode);
		StringBuilder error = new StringBuilder();
		error.append(String.valueOf(tagDriverError.getErrorCode())).append("::")
				.append(tagDriverError.getErrorDescription()).append("==>").append(Arrays.deepToString(stackTrace));
		if (log.isErrorEnabled()) {
			log.error(error.toString());
		}
	}

}
