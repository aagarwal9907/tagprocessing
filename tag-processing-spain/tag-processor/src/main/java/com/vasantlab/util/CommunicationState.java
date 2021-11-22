package com.vasantlab.util;

import java.io.Serializable;

public enum CommunicationState implements Serializable{
	START("30"), WRITESTART("02"), READSTART("04"), ERRORCODE("08"),CONFIGPT1("81"),CONFIGPT2("82"),CONFIGPT3("83"),CONFIGPT4("84"),
	CONFIGPT5("85"),CONFIGPT6("86"),CONFIGPT7("87"),CONFIGM11("88"),CONFIGM21("89"),CONFIGM31("8A"),CONFIGM12("8B"),CONFIGM22("8C"),CONFIGM32("8D"),
	CONFIGM13("B0"),CONFIGM23("B1"),CONFIGM33("B2"),CONFIGM14("B3"),CONFIGM24("B4"),CONFIGM34("B5"),CONFIGDP1("B6"),CONFIGDP2("B7"),CONFIGDP3("B8");

	private String commState;

	private CommunicationState(String communicationState) {
		this.commState = communicationState;

	}

	public String getCommState() {
		return commState;
	}

	public static CommunicationState fromString(String text) {
		for (CommunicationState state : CommunicationState.values()) {
			if (state.commState.equalsIgnoreCase(text)) {
				return state;
			}
		}
		return null;
	}
}
