package com.vasanthlab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.vasanthlab.dao.data.service.interfaces.RFIDDataService;
import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.model.TagProcessingInput;

@Controller
public class ReportController {
	
	@Autowired
	private RFIDDataService rfidDataService;
	
	@MessageMapping("/tagging/report")
	@SendTo("/topic/feed")
	public List<RFIDData> generateReport(TagProcessingInput input) {
		return  rfidDataService.finddata(input.getOrderId(), input.getBarcode());
	}
}
