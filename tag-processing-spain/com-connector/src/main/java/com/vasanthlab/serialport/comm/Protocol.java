package com.vasanthlab.serialport.comm;

public interface Protocol {
	void onReceive(byte b);

	void onStreamClosed();
}
