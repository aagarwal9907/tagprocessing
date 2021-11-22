package com.vasantlab.util;

import org.springframework.stereotype.Service;

import com.thingmagic.TagReadData;
import com.vasantlab.model.ProcessingContext;
import com.vasantlab.model.TagInfo;
import com.vasantlab.tag.event.TagDataEvent;
import com.vasantlab.tag.service.exception.TagStatus;

@Service("tagOperationService")
public class TagValidation {

    public TagInfo epcValidation(ProcessingContext processingContext, String newEPC, String accessPw, String killPw,
	    String userMemory) {
	TagDataEvent dataEvent = processingContext.getSgDriver().getEvent();
	TagReadData[] list = dataEvent.getTagData();
	TagInfo tagInfo = new TagInfo();
	TagStatus status = TagStatus.VALIDATIONFAIL;

	for (TagReadData d : list) {
	    tagInfo.setReadCount(d.getReadCount());
	    tagInfo.setRssi(d.getRssi());
	    tagInfo.setTimeStamp(d.getTime());

	}
	if ((newEPC.equalsIgnoreCase(dataEvent.getNewEPC())) && (accessPw.equalsIgnoreCase(dataEvent.getAccessPw()))
		&& (killPw.equalsIgnoreCase(dataEvent.getKillPw())) && (userMemory.equals(dataEvent.getUserMemory()))) {
	    tagInfo.setTidData(dataEvent.getTidData());
	    status = TagStatus.VALIDATIONOK;
	    tagInfo.setTagStatus(status);

	} else {
	    tagInfo.setTagStatus(status);
	}
	return tagInfo;
    }

}
