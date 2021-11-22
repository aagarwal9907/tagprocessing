package com.vasantlab.tagdriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vasantlab.factory.RFIDTagAdaptorFactory;

@Service
public class RFIdDriverReference {
	@Autowired
	private RFIDTagAdaptorFactory rfidTagFactory;

	@Value("${rfid.driver.name}")
	private String rfidDriverName;

	public RFIdInterfaceAdaptor getDriverReference() {
		return rfidTagFactory.getAdaptorByName(rfidDriverName);

	}
}
