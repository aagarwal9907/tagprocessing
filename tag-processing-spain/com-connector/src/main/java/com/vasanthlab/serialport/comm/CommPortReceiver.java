package com.vasanthlab.serialport.comm;

import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;
import javax.annotation.PostConstruct;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
/**
 * This class handles the read of COMPORT data along with sending out the received data as application event 
 * 
 */
@Component
public class CommPortReceiver implements ApplicationEventPublisherAware, SerialPortEventListener {
	
	@Autowired
	private TwoWaySerialComm twoWaySerialComm;

	private ApplicationEventPublisher publisher;

	private InputStream in;
	
	private ComPortErrorHandler errorHandler = new ComPortErrorHandler();
	
	public CommPortReceiver(){}
	
	@PostConstruct 
	public void setup() { 
		  this.setInputStream(twoWaySerialComm.getIn());
		  try {
			twoWaySerialComm.getSerialPort().addEventListener(this);
		} catch (TooManyListenersException e) {
			errorHandler.getComPortError(10002,e.getStackTrace());
		}
		  twoWaySerialComm.getSerialPort().notifyOnDataAvailable(true);
	  }
	 
	
	public void setInputStream(InputStream in){
		this.in=in;
	}
	
	/**
	 * Implements <code>SerialPortEventListener</code> serialEvent method. 
	 * Receiving read event from the COM device to process received data bits. 
	 * @see TwoWaySerialComm
	 */
	@Override
	public void serialEvent(SerialPortEvent arg0) {
		int data;
		try {
			int len = 0;
			byte[] buffer = new byte[8];
			while ((data = in.read()) > -1) {
				if (data == '\n') {
					break;
				}
				buffer[len++] = (byte) data;
			}
			
			if (String.valueOf(Hex.encodeHex(buffer)).startsWith("aa")) {
				publisher.publishEvent(new CommPortEvent(Hex.encodeHex(buffer)));
			} 
		} catch (IOException e) {
			errorHandler.getComPortError(10001,e.getStackTrace());
		}
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}