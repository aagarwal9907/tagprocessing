package com.vasanthlab.serialport.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolImpl implements Protocol {
	private static Logger log = LoggerFactory.getLogger(ProtocolImpl.class);
	private byte[] buffer = new byte[1024];
	private int tail = 0;

	public void onReceive(byte b) {
		if (b == '\n') {
			onMessage();
		} else {
			buffer[tail] = b;
			tail++;
		}
	}

	public void onStreamClosed() {
		onMessage();
	}

	/**
	 * When message is recognized onMessage is invoked
	 */
	private void onMessage() {
		if (tail != 0) {
			// constructing message
			String message = getMessage(buffer, tail);
			log.info("RECEIVED MESSAGE: {} " , message);

			// this logic should be placed in some kind of
			// message interpreter class not here
			if ("HELO".equals(message)) {
				// CommPortSender.send(getMessage("OK"));
			} else if ("OK".equals(message)) {
				// CommPortSender.send(getMessage("OK ACK"));
			}
			tail = 0;
		}
	}

	// helper methods
	public byte[] getMessage(String message) {
		return (message + "\n").getBytes();
	}

	public String getMessage(byte[] buffer, int len) {
		return new String(buffer, 0, tail);
	}
}
