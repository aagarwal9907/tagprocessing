package com.vasantlab.tag.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasantlab.factory.TagStateFactory;
import com.vasantlab.util.CommunicationState;

@Service("tagContext")
public class TagContext {

	private static Logger log = LoggerFactory.getLogger(TagContext.class);

	@Autowired
	private TagStateFactory tagStateFactory;

	public void processCommPortData(char[] data) {
		if (data != null) {
			StringBuilder opCode = new StringBuilder();
			
			opCode.append(data[8]).append(data[9]);
			CommunicationState stateId = null;
			
			stateId = CommunicationState.fromString(opCode.toString());
			
			if (stateId != null) {
				TagCommunicationStateInterface tagCommunicationStateInterface = tagStateFactory.getStateObject(stateId);
				tagCommunicationStateInterface.doAction(stateId.toString());
			}
		} else {
			log.error("Data received from device is null");
		}
	}
}