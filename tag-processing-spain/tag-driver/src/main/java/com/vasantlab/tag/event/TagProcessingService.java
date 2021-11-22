package com.vasantlab.tag.event;

import com.thingmagic.TagData;

public class TagProcessingService {
 
	private TagData tagData;
	
	public TagProcessingService(String epc){
		tagData=new TagData(epc);
	}
	
	public TagData getTagData() {
		return tagData;
	}

	@Override
	public String toString(){
		return new StringBuilder("[EPC:").append(tagData.epcString()).append("]").toString();		
	}
}
