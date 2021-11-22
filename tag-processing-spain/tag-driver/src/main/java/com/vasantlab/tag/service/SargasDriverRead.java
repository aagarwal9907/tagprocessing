package com.vasantlab.tag.service;

import com.thingmagic.ReaderException;
import com.vasantlab.tag.interfaces.TagDriver;
import com.vasantlab.tag.model.SargasDriver;
import com.vasantlab.tag.model.TagDataStatus;
import com.vasantlab.tag.service.exception.TagDriverErrorHandler;
import com.vasantlab.tag.service.exception.TagStatus;

public class SargasDriverRead implements TagDriver {
    private SargasDriver driver;
    private TagDriverErrorHandler driverErrorHandler = new TagDriverErrorHandler();

    public SargasDriverRead(SargasDriver driver) {
	this.driver = driver;
    }

    @Override
    public TagDataStatus execute() {
	TagDataStatus readStatus = new TagDataStatus();
	try {
	    readStatus = driver.read();

	    if (readStatus.isStatus()) {
		readStatus.setTagStatus(TagStatus.READOK);
	    }

	} catch (ReaderException e) {
	    driverErrorHandler.getTagDriverError(20014, e.getStackTrace());
	    readStatus.setTagStatus(TagStatus.READFAIL);
	} catch (Exception e) {
	    driverErrorHandler.getTagDriverError(20014, e.getStackTrace());
	    readStatus.setTagStatus(TagStatus.READFAIL);
	}

	return readStatus;
    }
}
