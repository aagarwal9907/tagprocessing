package com.vasanthlab.serialport.comm;

import org.springframework.context.ApplicationEvent;

public class CommPortEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private char[] data;
	private byte[] Senddata;
	

	public char[] getData() {
		return data;
	}

	public byte[] getSendData() {
		return Senddata;
	}
	
	public CommPortEvent(byte[] source) {
		super(source);
		Senddata = source;
	}

	public CommPortEvent(char[] source) {
		super(source);
		data = source;
	}

}
