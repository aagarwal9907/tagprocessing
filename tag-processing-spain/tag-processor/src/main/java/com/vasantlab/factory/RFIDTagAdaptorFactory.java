package com.vasantlab.factory;

import java.util.HashMap;
import java.util.Map;

import com.vasantlab.tagdriver.RFIdInterfaceAdaptor;

public final class RFIDTagAdaptorFactory {

	private Map<String, RFIdInterfaceAdaptor> availableDrivers = new HashMap<>();

	public void register(String name, RFIdInterfaceAdaptor adaptor) {
		availableDrivers.put(name, adaptor);
	}

	public RFIdInterfaceAdaptor getAdaptorByName(String name) {
		return availableDrivers.get(name);
	}

}
