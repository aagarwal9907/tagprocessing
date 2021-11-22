package com.vasanthlab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/v1/search")
public class SearchController {

//	@Autowired
//	RFIDFileProcessService rfIDFileProcessService;

	@RequestMapping(method = RequestMethod.GET, path = "/order/{orderid}")
	public ResponseEntity<String> checkIfOrderExists(@PathVariable String orderid) {
		return new ResponseEntity("yes", HttpStatus.OK);
	}

	/*@RequestMapping(method = RequestMethod.GET, path = "/test")
	public ResponseEntity<String> test() {
		rfIDFileProcessService.tagDataPersists("144258", "2568548568102");
		return new ResponseEntity<>("yes", HttpStatus.OK);
	}*/

	
}
