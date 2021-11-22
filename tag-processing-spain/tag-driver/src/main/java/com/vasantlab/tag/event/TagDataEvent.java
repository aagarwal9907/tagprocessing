package com.vasantlab.tag.event;

import com.thingmagic.TagReadData;

public class TagDataEvent {
	
	private TagReadData[] tagData;
	
	private String newEPC;
	
	private String accessPw;
	
	private String killPw;
	
	private String userMemory;
	
	private String newEPCLock;
	
	private String accessPwLock;
	
	private String killPwLock;
	
	private String userMemoryLock;
	
	private String tidData;
	

	public String getTidData() {
		return tidData;
	}

	public void setTidData(String tidData) {
		this.tidData = tidData;
	}

	public String getNewEPCLock() {
		return newEPCLock;
	}

	public void setNewEPCLock(String newEPCLock) {
		this.newEPCLock = newEPCLock;
	}

	public String getAccessPwLock() {
		return accessPwLock;
	}

	public void setAccessPwLock(String accessPwLock) {
		this.accessPwLock = accessPwLock;
	}

	public String getKillPwLock() {
		return killPwLock;
	}

	public void setKillPwLock(String killPwLock) {
		this.killPwLock = killPwLock;
	}

	public String getUserMemoryLock() {
		return userMemoryLock;
	}

	public void setUserMemoryLock(String userMemoryLock) {
		this.userMemoryLock = userMemoryLock;
	}

	public String getAccessPw() {
		return accessPw;
	}

	public void setAccessPw(String accessPw) {
		this.accessPw = accessPw;
	}

	public String getKillPw() {
		return killPw;
	}

	public void setKillPw(String killPw) {
		this.killPw = killPw;
	}

	public String getUserMemory() {
		return userMemory;
	}

	public void setUserMemory(String userMemory) {
		this.userMemory = userMemory;
	}

	public TagReadData[] getTagData() {
		return tagData;
	}

	public void setTagData(final TagReadData[] tagData) {
		this.tagData = tagData;
	}

	public String getNewEPC() {
		return newEPC;
	}

	public void setNewEPC(String newEPC) {
		this.newEPC = newEPC;
	}
	
	
	
}
