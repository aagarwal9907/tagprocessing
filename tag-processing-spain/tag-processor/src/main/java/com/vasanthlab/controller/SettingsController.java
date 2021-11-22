package com.vasanthlab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vasantlab.model.CommPortDataInput;
import com.vasantlab.tag.processingservice.interfaces.CommPortReadCommandService;
import com.vasantlab.tag.processingservice.interfaces.CommPortWriteCommandService;
/**
 * This controller handles all the com device setting parameters it has read and write operation api's
 * @author Shubha
 *
 */
@RestController
@RequestMapping("api/v1/tagging")
public class SettingsController {
	private static Logger log = LoggerFactory.getLogger(SettingsController.class);
	@Autowired
	private CommPortWriteCommandService commPortwriteCommandService;
	
	@Autowired
	private CommPortReadCommandService commPortReadConfigService;
	
	/**
	 * sends a read operation command using <code>CommPortDataInput</code> to COM device
	 * @param input
	 * @return CommPortDataInput
	 */
	@RequestMapping(method=RequestMethod.POST,path="/settings/read",consumes = "application/json",produces="application/json")
	public CommPortDataInput getComDeviceConfigForCommand(@RequestBody CommPortDataInput input) {
		commPortwriteCommandService.writeCommand(input);
		int dataForCommand=-1;
		do {
			dataForCommand=commPortReadConfigService.getData();
			log.info("Checking for data {}",dataForCommand );
		}while(dataForCommand<=-1);
		input.setCommandData(dataForCommand);
		
		return input;
	}
	
	/**
	 * sends a write operation command using <code>CommPortDataInput</code> to COM device
	 * @param input
	 * @return CommPortDataInput
	 */
	@RequestMapping(method=RequestMethod.POST,path="/settings/write",consumes = "application/json",produces="application/json")
	public CommPortDataInput setComDeviceConfigForCommand(@RequestBody CommPortDataInput input) {
		commPortwriteCommandService.writeCommand(input);
			
		return input;
	}

}
