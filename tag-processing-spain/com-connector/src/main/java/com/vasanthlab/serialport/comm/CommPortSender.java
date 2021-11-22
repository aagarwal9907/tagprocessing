package com.vasanthlab.serialport.comm;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
/**
 * This class writes bytes received from SW->HW as write data on the com port output stream 
 *
 */
@Component
public class CommPortSender implements ApplicationListener<CommPortEvent> {

	
	@Autowired
	private TwoWaySerialComm twoWaySerialComm;

	private ComPortErrorHandler errorHandler = new ComPortErrorHandler();
	
	public void send(byte[] bytes) {
		try {
			if (bytes != null) {
				
				// sending through serial port is simply writing into
				// OutputStream
				twoWaySerialComm.getOut().write(bytes);
				twoWaySerialComm.getOut().flush();
			}
		}

		catch (IOException e) {
			errorHandler.getComPortError(10003,e.getStackTrace());
		}

	}

	
	public void onApplicationEvent(CommPortEvent event) {
		CommPortEvent commPortEvent = event;
		byte[] receive = commPortEvent.getSendData();
		send(receive);

	}

}