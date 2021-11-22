package com.vasanthlab.serialport.comm;

import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CommPortSenderTest implements ApplicationListener<CommPortEvent>, ApplicationEventPublisherAware {

    private static Logger log = LoggerFactory.getLogger(CommPortSenderTest.class);

    private ApplicationEventPublisher publisher;

    private byte[] STARTOKCOMMAND = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x31,
	    (byte) 0x30, (byte) 0x30 };

    private byte[] WRITEOKCOMMAND = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x03,
	    (byte) 0x30, (byte) 0x30 };

    private byte[] READOKCOMMAND = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x05,
	    (byte) 0x30, (byte) 0x30 };

    private byte[] READEFAILCOMMAND = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x06,
	    (byte) 0x30, (byte) 0x30 };

    private byte[] WRITESTART = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x02,
	    (byte) 0x30, (byte) 0x30 };

    private byte[] ERROR1 = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x03, (byte) 0x08, (byte) 0x00,
	    (byte) 0x33, (byte) 0x30 };
    private byte[] ERRPC2HW = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x03, (byte) 0x90, (byte) 0x00,
	    (byte) 0x30, (byte) 0x30 };

    private byte[] START = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x30, (byte) 0x31 };

    public void getData(byte[] bytes) {
	log.info("Using Sim to send bytes");
	if (Arrays.equals(bytes, STARTOKCOMMAND)) {
	    publisher.publishEvent(new CommPortEvent(Hex.encodeHex(ERROR1)));
	} else if (Arrays.equals(bytes, ERRPC2HW)) {
	    publisher.publishEvent(new CommPortEvent(Hex.encodeHex(START)));
	}
    }

    public void getData1(byte[] bytes) {
	log.info("Using Sim to send bytes");
	if (Arrays.equals(bytes, STARTOKCOMMAND)) {
	    publisher.publishEvent(new CommPortEvent(Hex.encodeHex(
		    new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x02, (byte) 0x32 })));
	} else if (Arrays.equals(bytes, WRITEOKCOMMAND)) {
	    publisher.publishEvent(new CommPortEvent(Hex.encodeHex(
		    new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x04, (byte) 0x33 })));
	} else if (Arrays.equals(bytes, READOKCOMMAND)) {
	    publisher.publishEvent(new CommPortEvent(Hex.encodeHex(
		    new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x02, (byte) 0x32 })));
	} else if (Arrays.equals(bytes, READEFAILCOMMAND)) {
	    publisher.publishEvent(new CommPortEvent(Hex.encodeHex(WRITESTART)));
	} else if (Arrays.equals(bytes, WRITESTART)) {
	    publisher.publishEvent(new CommPortEvent(Hex.encodeHex(
		    new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x02, (byte) 0x32 })));
	}
    }

    public void sendTest(byte[] bytes) {
	if (bytes != null) {
	    if (log.isInfoEnabled()) {
		log.info("data sent {}", Arrays.toString(bytes));
	    }
	    getData(bytes);
	}
    }

    @Override
    public void onApplicationEvent(CommPortEvent event) {
	CommPortEvent commPortEvent = event;
	byte[] receive = commPortEvent.getSendData();
	sendTest(receive);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
	this.publisher = applicationEventPublisher;

    }
}
