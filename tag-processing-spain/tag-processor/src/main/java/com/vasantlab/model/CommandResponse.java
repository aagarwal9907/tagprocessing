package com.vasantlab.model;

import java.util.Observable;

public class CommandResponse extends Observable {
	
	private Integer commandData;
	
	public CommandResponse(int data) {
		commandData=data;
	}

	
	
	public Integer getCommandData() {
		return commandData;
	}

	public void setCommandData(Integer commandData) {
		this.commandData = commandData;
		setChanged();
	    notifyObservers();
	}
	
	

}
