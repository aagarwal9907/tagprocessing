package com.vasantlab.model;

import java.util.Stack;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.vasantlab.util.ErrorCommunicationState;
/**
 * <p> Stores all the error message and Byte Codes for responses as per the COM device Specification guide</p>
 * @author Shubha
 *
 */
public class DeviceError {
	
	private String message;
	private ErrorCommunicationState state;
	private static Stack<DeviceError> error=new Stack<>();
	
	public DeviceError(String message, ErrorCommunicationState state) {
		this.message = message;
		this.state = state;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ErrorCommunicationState getState() {
		return state;
	}
	public void setState(ErrorCommunicationState state) {
		this.state = state;
	}
	
	public static void push(DeviceError handler) {
		DeviceError.error.push(handler);
	}
	
	public static DeviceError pop() {
		if(hasError()) {
		return DeviceError.error.pop();
		}
		return null;
	}
	
	public static Stack<DeviceError> getError(){
		return DeviceError.error;
	}
	
	public static boolean hasError() {
		if(!DeviceError.error.isEmpty() && DeviceError.error.peek()!=null) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return	ToStringBuilder.reflectionToString(this);
	}
	
}
