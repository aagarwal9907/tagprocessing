package com.vasantlab.util;

import java.io.Serializable;

public enum CommPortReadConfigurationCommand implements Serializable {
 PT1("81"),PT2("82"),PT3("83"),PT4("84"),PT5("85"),PT6("86"),PT7("87"),M11("88"),M21("89"),M31("8A"),M12("8B"),M22("8C"),M32("8D")
 ,M13("B0"),M23("B1"),M33("B2"),M14("B3"),M24("B4"),M34("B5"),DP1("B6"),DP2("B7"),DP3("B8");
 
private String readConfigurationCommand;


private CommPortReadConfigurationCommand(String readConfigurationCommand) {
		this.readConfigurationCommand = readConfigurationCommand;
	}



public String getReadConfigurationCommand() {
		return readConfigurationCommand;
	}



public static CommPortReadConfigurationCommand fromString(String text) {
	for (CommPortReadConfigurationCommand command : CommPortReadConfigurationCommand.values()) {
		if (command.getReadConfigurationCommand().equalsIgnoreCase(text)) {
			return command;
		}
	}
	return null;
}
}
