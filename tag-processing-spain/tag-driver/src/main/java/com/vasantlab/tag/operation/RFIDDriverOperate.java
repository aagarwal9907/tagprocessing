package com.vasantlab.tag.operation;

import com.vasantlab.tag.interfaces.TagDriver;
import com.vasantlab.tag.model.TagDataStatus;


public class RFIDDriverOperate {

	public TagDataStatus executeOperation(TagDriver driver) {
		return driver.execute();
	}
}
