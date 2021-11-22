package com.vasantlab.model;

public final class DeviceReadyEvent {
	
	private static boolean deviceReady;

	public static boolean isDeviceReady() {
		return deviceReady;
	}

	public static void setDeviceReady(boolean deviceReady) {
		DeviceReadyEvent.deviceReady = deviceReady;
	}
}
