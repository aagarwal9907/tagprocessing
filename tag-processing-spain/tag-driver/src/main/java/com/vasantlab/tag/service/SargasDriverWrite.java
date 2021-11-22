package com.vasantlab.tag.service;


import com.vasantlab.tag.interfaces.TagDriver;
import com.vasantlab.tag.model.SargasDriver;
import com.vasantlab.tag.model.TagDataStatus;
import com.vasantlab.tag.service.exception.DriverEventException;
import com.vasantlab.tag.service.exception.TagDriverErrorHandler;
import com.vasantlab.tag.service.exception.TagStatus;

public class SargasDriverWrite implements TagDriver {

	private SargasDriver driver;
	private TagDriverErrorHandler driverErrorHandler = new TagDriverErrorHandler();
	public SargasDriverWrite(SargasDriver driver) {
		this.driver = driver;
	}

	public TagDataStatus execute() {
		TagDataStatus status = new TagDataStatus();
		try {
			status = driver.write();
			
			if (status.isStatus()) {
				status.setTagStatus(TagStatus.WRITEOK ) ;
			}else {
				status.setTagStatus(TagStatus.WRITEFAIL) ;
			}
				
		} catch (DriverEventException e) {
			driverErrorHandler.getTagDriverError(20013, e.getStackTrace());
			status.setTagStatus(TagStatus.WRITEFAIL) ;
		}
		
		return status;
				
	}

}
