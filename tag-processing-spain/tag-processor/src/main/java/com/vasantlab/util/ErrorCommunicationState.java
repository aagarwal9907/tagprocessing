package com.vasantlab.util;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>
 * Follows com port device error specifications to create enum to be used for
 * error notification and handling by operator or reaction by operator
 * </p>
 * 
 * @author Shubha
 *
 */
public enum ErrorCommunicationState implements Serializable {
    ERROR_0033("0033", "No Tag above Writer & Reader", CommPortCommandUtil.ERRORCODECOMMAND),
    ERROR_0133("0133", "Tag out of Hopper but not above Writer", CommPortCommandUtil.ERRORCODECOMMAND),
    ERROR_0233("0233", "Tag not out of Hopper but reached above Writer", CommPortCommandUtil.ERRORCODECOMMAND),
    ERROR_0333("0333", "Tag out of Writer but not reached Reader", null),
    ERROR_0433("0433", "Tag reached Reader, No tag above Writer (Is this is the last Tag!)", null),
    ERROR_0533("0533", "Tag reached Reader, Tag out of Hopper but not above Writer",
	    CommPortCommandUtil.ERRORCODECOMMAND),
    ERROR_F533("F533", "Tag not moved out of Writer", null),
    ERROR_020A("020A", "Error before tag on writter 1st time", null),
    ERROR_030A("030A", "Error before tag on writter 1st time", null),
    ERROR_0000("0000", "No more tags to process.", null);

    private String errorState;
    private String errorCode;
    private byte[] commandByte;

    private ErrorCommunicationState(String errorCode, String communicationState, byte[] command) {
	this.errorState = communicationState;
	this.errorCode = errorCode;
	if (command != null) {
	    commandByte = Arrays.copyOf(command, command.length);
	}
    }

    public String getErrorState() {
	return this.errorState;
    }

    public String getErrorCode() {
	return this.errorCode;
    }

    public byte[] getCommandByte() {
	return commandByte;
    }

    public static ErrorCommunicationState fromString(String error) {
	for (ErrorCommunicationState state : ErrorCommunicationState.values()) {
	    if (state.getErrorCode().equalsIgnoreCase(error)) {
		return state;
	    }
	}
	return null;
    }

}
