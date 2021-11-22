package com.vasantlab.tagdriver;

import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.data.tables.TagDataFeed;
import com.vasantlab.model.ProcessingContext;
import com.vasantlab.tag.model.SargasDriver;
import com.vasantlab.tag.model.TagDataStatus;
import com.vasantlab.tag.operation.RFIDDriverOperate;
import com.vasantlab.tag.service.SargasDriverRead;
import com.vasantlab.tag.service.SargasDriverWrite;

public class SargasRFIdAdaptor implements RFIdInterfaceAdaptor {

	private RFIDDriverOperate rfidDriverOperate;

	private SargasDriver sgDriver;

	private ProcessingContext processingContext;

	public SargasRFIdAdaptor(RFIDDriverOperate operator, SargasDriver sgDriver) {
		this.rfidDriverOperate = operator;
		this.sgDriver = sgDriver;
		processingContext = new ProcessingContext();
	}

	public ProcessingContext tagRead() {

		SargasDriverRead read = new SargasDriverRead(sgDriver);
		TagDataStatus readStatus = rfidDriverOperate.executeOperation(read);
		processingContext.setSgDriver(sgDriver);
		processingContext.setTagError(readStatus.getTagStatus());
		processingContext.setStatus(readStatus.getTagDataMemoryStatus());
		return processingContext;

	}

	public ProcessingContext tagWrite(RFIDData rfDetails,TagDataFeed tagDataFeed) {
		sgDriver.getEvent().setNewEPC(rfDetails.getEpc());
		sgDriver.getEvent().setAccessPw(rfDetails.getAccess_Password());
		sgDriver.getEvent().setKillPw(rfDetails.getKill_Password());
		sgDriver.getEvent().setUserMemory(rfDetails.getUser_Memory());
		sgDriver.getEvent().setAccessPwLock(tagDataFeed.getAccess_password());
		sgDriver.getEvent().setKillPwLock(tagDataFeed.getKill_password());
		sgDriver.getEvent().setNewEPCLock(tagDataFeed.getEpc_code());
		sgDriver.getEvent().setUserMemoryLock(tagDataFeed.getUser_memory());
		SargasDriverWrite writer = new SargasDriverWrite(sgDriver);
		TagDataStatus writeStatus = rfidDriverOperate.executeOperation(writer);
		processingContext.setSgDriver(sgDriver);
		processingContext.setTagError(writeStatus.getTagStatus());
		processingContext.setStatus(writeStatus.getTagDataMemoryStatus());
		return processingContext;

	}

	

}
