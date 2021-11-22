package com.vasantlab.model;

public class CommPortDataInput {
	private String command;
	private int commandData;
	private String commandStatus;

	public String getCommandStatus() {
		return commandStatus;
	}

	public void setCommandStatus(String commandStatus) {
		this.commandStatus = commandStatus;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getCommandData() {
		return commandData;
	}

	public void setCommandData(int commandData) {
		this.commandData = commandData;
	}

	@Override
	public String toString() {
		return "CommPortDataInput [command=" + command + ", commandData=" + commandData + ", commandStatus="
				+ commandStatus + ", getCommandStatus()=" + getCommandStatus() + ", getCommand()=" + getCommand()
				+ ", getCommandData()=" + getCommandData() + "]";
	}
}
