package com.vasantlab.util;

import java.io.Serializable;

public enum CommPortWriteConfigurationCommand implements Serializable {
	 WRITE_PT1("91",(byte)(0x91),null), WRITE_PT2("92",(byte)(0x92),null),WRITE_PT3("93",(byte)(0x93),null), WRITE_PT4("94",(byte)(0x94),null), WRITE_PT5("95",(byte)(0x95),null),
	 WRITE_PT6 ("96",(byte)(0x96),null), WRITE_PT7("97",(byte)(0x97),null),WRITE_M11 ("98",(byte)(0x98),null), WRITE_M21("99",(byte)(0x99),null),
	 WRITE_M31("9A",(byte)(0x94),null), WRITE_M12("9B",(byte)(0x9B),null),WRITE_M22("9C",(byte)(0x9C),null),WRITE_M32("9D",(byte)(0x9D),null),
	 WRITE_M13("A0",(byte)(0xA0),null),WRITE_M23("A1",(byte)(0xA1),null),WRITE_M33 ("A2",(byte)(0xA2),null),WRITE_M14 ("A3",(byte)(0xA3),null),WRITE_M24("A4",(byte)(0xA4),null),
	 WRITE_M34("A5",(byte)(0xA5),null),WRITE_DP1 ("A6",(byte)(0xA6),null), WRITE_DP2("A7",(byte)(0xA7),null),WRITE_DP3 ("A8",(byte)(0xA8),null),
	 
	 
	 
	 READ_PT1("81",(byte)(0x00),CommPortCommandUtil.READ_PT1), 
	 READ_PT2("82",(byte)(0x00),CommPortCommandUtil.READ_PT2),
	 READ_PT3("83",(byte)(0x00),CommPortCommandUtil.READ_PT3),
	 READ_PT4("84",(byte)(0x00),CommPortCommandUtil.READ_PT4), 
	 READ_PT5("85",(byte)(0x00),CommPortCommandUtil.READ_PT5),
	 READ_PT6 ("86",(byte)(0x00),CommPortCommandUtil.READ_PT6),
	READ_PT7("87",(byte)(0x00),CommPortCommandUtil.READ_PT7),
	READ_M11 ("88",(byte)(0x00),CommPortCommandUtil.READ_M11),
	READ_M21("89",(byte)(0x00),CommPortCommandUtil.READ_M21),
	READ_M31("8A",(byte)(0x00),CommPortCommandUtil.READ_M31),
	READ_M12("8B",(byte)(0x00),CommPortCommandUtil.READ_M12),
	READ_M22("8C",(byte)(0x00),CommPortCommandUtil.READ_M22),
	READ_M32("8D",(byte)(0x00),CommPortCommandUtil.READ_M32),
	READ_M13("B0",(byte)(0x00),CommPortCommandUtil.READ_M13),
	READ_M23("B1",(byte)(0x00),CommPortCommandUtil.READ_M23),
	READ_M33 ("B2",(byte)(0x00),CommPortCommandUtil.READ_M33),
	READ_M14 ("B3",(byte)(0x00),CommPortCommandUtil.READ_M14),
	READ_M24("B4",(byte)(0x00),CommPortCommandUtil.READ_M24),
	READ_M34("B5",(byte)(0x00),CommPortCommandUtil.READ_M34),
	READ_DP1 ("B6",(byte)(0x00),CommPortCommandUtil.READ_DP1),
	READ_DP2("B7",(byte)(0x00),CommPortCommandUtil.READ_DP2),
	READ_DP3("B8",(byte)(0x00),CommPortCommandUtil.READ_DP3);

	private String writeConfigurationCommand;
	private byte writeCommandByte;
	private byte[] readCommandByte;
	
	private CommPortWriteConfigurationCommand(String writeConfigurationCommand, byte writeCommandByte,
			byte[] readCommandByte) {
		this.writeConfigurationCommand = writeConfigurationCommand;
		this.writeCommandByte = writeCommandByte;
		this.readCommandByte = readCommandByte;
	}


	public byte getWriteCommandByte() {
		return writeCommandByte;
	}


	public byte[] getReadCommandByte() {
		return readCommandByte;
	}


	public String getWriteConfigurationCommand() {
		return writeConfigurationCommand;
	}



	public static CommPortWriteConfigurationCommand fromString(String text) {
			for (CommPortWriteConfigurationCommand command : CommPortWriteConfigurationCommand.values()) {
				if (command.name().equalsIgnoreCase(text)) {
					return command;
				}
			}
			return null;
		}
}
