package com.vasantlab;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasantlab.model.CommPortDataInput;

public class CommDataInputTest {

	@Test
	public void test() throws JsonProcessingException {
		CommPortDataInput input=new CommPortDataInput();
		input.setCommand("PT1");
		input.setCommandData(0);
		input.setCommandStatus("READ");
		ObjectMapper mapper=new ObjectMapper();
		System.out.println(mapper.writeValueAsString(input));
	}

}
