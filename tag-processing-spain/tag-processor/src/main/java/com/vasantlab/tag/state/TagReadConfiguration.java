package com.vasantlab.tag.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasantlab.tag.processingservice.interfaces.CommPortReadCommandService;
import com.vasantlab.util.CachingUtil;

@Service("readConfigService")
public class TagReadConfiguration implements TagCommunicationStateInterface {
@Autowired
private CommPortReadCommandService commPortReadConfigService;
	@Override
	public void doAction(String stateId) {
		commPortReadConfigService.readCommand(CachingUtil.cachingReceiveByte.get("receiveByte"));
		
	}

}