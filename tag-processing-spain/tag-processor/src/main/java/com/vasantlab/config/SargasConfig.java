package com.vasantlab.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sargas")
public class SargasConfig {

	private String url;
	private String antenna;
	private Integer read;
	private Integer write;
	private boolean shutdownEnable;
	
	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Integer getWrite() {
		return write;
	}

	public void setWrite(Integer write) {
		this.write = write;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;

	}

	public int[] getAntennaAsIntArray() {
		String[] parts = antenna.split(",");
		int[] intArray = new int[parts.length];
		for (int i = 0; i < parts.length; i++) {
			intArray[i] = Integer.parseInt(parts[i]);
		}
		return intArray;
	}

	public String getAntenna() {
		return antenna;
	}

	public void setAntenna(String antenna) {
		this.antenna = antenna;
	}

	public boolean getShutdownEnable() {
		return shutdownEnable;
	}

	public void setShutdownEnable(boolean shutdownEnable) {
		this.shutdownEnable = shutdownEnable;
	}

}
