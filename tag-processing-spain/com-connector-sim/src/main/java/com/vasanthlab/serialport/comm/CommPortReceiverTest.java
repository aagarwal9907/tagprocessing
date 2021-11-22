package com.vasanthlab.serialport.comm;



import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CommPortReceiverTest implements ApplicationEventPublisherAware {
	private static Logger log = LoggerFactory.getLogger(CommPortReceiverTest.class);
	private ApplicationEventPublisher publisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	@EventListener
	public void push(ContextRefreshedEvent event) {
		try {
			Thread.sleep(4000);
			publisher.publishEvent(new CommPortEvent(Hex.encodeHex(
					new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02, (byte) 0x30, (byte) 0x31 })));
		} catch (Exception e) {
			log.error("Push failed via sim", e);
		}
	}


}
