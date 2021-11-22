package com.vasantlab;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.codec.DecoderException;
import org.junit.Assert;
import org.junit.Test;


public class TestHex {

	@Test
	public void testHex() throws DecoderException {

		String hex = "3039742840f5270000000000";
		BigInteger decimal = new BigInteger(hex, 16);
		for (int i = 0; i < 2; i++) {
			decimal = decimal.add(BigInteger.ONE);
			// System.out.println("Tag " + i + " :" + decimal.toString(16));
		}
	}

	@Test
	public void testDate() {
	/*	OrdinaryDataRFDetails ord = new OrdinaryDataRFDetails();
		ord.setCreate_date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		ord.setModify_date(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		System.out.println(ord.getCreate_date());
		Assert.assertNotNull(ord.getCreate_date());*/
	}
}
