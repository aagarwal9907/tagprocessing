package com.vasantlab.model;

import com.vasantlab.tag.service.exception.TagStatus;

public class TagInfo {
	private int rssi;
	private int readCount;
	private long timeStamp;
	private String tidData;
	TagStatus tagStatus;

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTidData() {
		return tidData;
	}

	public void setTidData(String tidData) {
		this.tidData = tidData;
	}

	public TagStatus getTagStatus() {
		return tagStatus;
	}

	public void setTagStatus(TagStatus tagStatus) {
		this.tagStatus = tagStatus;
	}
}
