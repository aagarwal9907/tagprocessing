package com.vasantlab.tag.processingservice;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.vasanthlab.serialport.comm.CommPortEvent;
import com.vasantlab.model.CommPortDataInput;
import com.vasantlab.tag.processingservice.interfaces.CommPortWriteCommandService;
import com.vasantlab.util.CommPortWriteConfigurationCommand;
import com.vasantlab.util.DataConverter;

@Service
public class CommPortWriteCommandServiceImpl implements CommPortWriteCommandService, ApplicationEventPublisherAware {
	private ApplicationEventPublisher publisher;

	@Override
	public void writeCommand(CommPortDataInput commPortDataInput) {
		byte [] comportData=null;
		if (commPortDataInput.getCommandStatus().equals("READ")) {
			StringBuilder command = new StringBuilder();
			command.append("READ_").append(commPortDataInput.getCommand());
			CommPortWriteConfigurationCommand readCommand = CommPortWriteConfigurationCommand
					.fromString(command.toString());
			publisher.publishEvent(new CommPortEvent(readCommand.getReadCommandByte()));
		} else if (commPortDataInput.getCommandStatus().equals("WRITE")) {
			byte byteData = 02;

			byte[] newByte;
			int data = commPortDataInput.getCommandData();
			StringBuilder command = new StringBuilder();
			command.append("WRITE_").append(commPortDataInput.getCommand());
			CommPortWriteConfigurationCommand readCommand = CommPortWriteConfigurationCommand
					.fromString(command.toString());
			byte commandByte = readCommand.getWriteCommandByte();
			String hexData = DataConverter.toHex(data);
			if (hexData.length()!= 0) {
				if(hexData.length()==3 ||hexData.length()==1) {
					StringBuilder comportStringData=new StringBuilder();
					comportStringData.append("0").append(hexData);
					comportData=DataConverter.hexStringToByteArray(comportStringData.toString());
				}else {
					comportData=DataConverter.hexStringToByteArray(hexData);	
				}
			}
			if(hexData.length()==0) {
				newByte = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, byteData,(byte)commandByte,(byte) 0x30,(byte) 0x30 };
				publisher.publishEvent(new CommPortEvent(newByte));
			}else if (hexData.length() == 2) {
				if(comportData != null) {
				byteData = 03;
				newByte = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, byteData,(byte)commandByte,comportData[0],(byte) 0x30,(byte) 0x30 };
				publisher.publishEvent(new CommPortEvent(newByte));
				}
			}else if(hexData.length() ==4){
				if(comportData != null) {
				byteData = 04;
				newByte = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, byteData,(byte)commandByte,comportData[0] ,comportData[1],(byte) 0x30,(byte) 0x30 };
				publisher.publishEvent(new CommPortEvent(newByte));
				}
			}
		}
		
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

}
