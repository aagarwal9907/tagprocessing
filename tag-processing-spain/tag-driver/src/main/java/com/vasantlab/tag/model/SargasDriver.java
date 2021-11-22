package com.vasantlab.tag.model;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import com.thingmagic.Gen2;
import com.thingmagic.ReaderException;
import com.thingmagic.ReaderUtil;
import com.thingmagic.TagData;
import com.thingmagic.TagFilter;
import com.thingmagic.TagOp;
import com.thingmagic.TagReadData;
import com.vasantlab.tag.event.TagDataEvent;
import com.vasantlab.tag.interfaces.DriverEntity;
import com.vasantlab.tag.service.exception.DriverEventException;
import com.vasantlab.tag.service.exception.TagDriverErrorHandler;
import com.vasantlab.tag.util.SargasRFIDReaderWriter;
import com.vasantlab.tag.util.TagLockOperation;

public class SargasDriver extends DriverEntity {

    private static final Logger log = LoggerFactory.getLogger(SargasDriver.class);

    private TagDataEvent event;
    private int writeAntennaNo;
    private int readAntennaNo;

    private TagDriverErrorHandler driverErrorHandler = new TagDriverErrorHandler();

    public int getReadAntennaNo() {
	return readAntennaNo;
    }

    public void setReadAntennaNo(int readAntennaNo) {
	this.readAntennaNo = readAntennaNo;
    }

    public int getWriteAntennaNo() {
	return writeAntennaNo;
    }

    public void setWriteAntennaNo(int writeAntennaNo) {
	this.writeAntennaNo = writeAntennaNo;
    }

    public void setEvent(TagDataEvent event) {
	this.event = event;
    }

    public TagDataEvent getEvent() {
	return event;
    }

    public TagDataStatus read() throws Exception {
	StopWatch watch = new StopWatch();
	watch.start("Read Processing Tag");

	TagReadData[] data = SargasRFIDReaderWriter.read();

	TagDataStatus tagDataStatus = new TagDataStatus();
	tagDataStatus.setStatus(true);
	Map<String, String> tagDataWriteMemoryStatus = new HashMap<>();

	if (event == null) {
	    event = new TagDataEvent();
	}
	if (data != null) {
	    // read EPC Data
	    event.setTagData(data);
	    if (event.getTagData() != null) {
		TagReadData[] list = event.getTagData();

		for (TagReadData d : list) {
		    log.info("Reading Process started for: {}", d.epcString());
		    // read Access Memory and Kill Password
		    if (event.getAccessPwLock().equalsIgnoreCase("locked")|| event.getAccessPwLock().equalsIgnoreCase("permenant")) {
			Map<String, String> reservedMemoryData = null;
			try {
			    reservedMemoryData = SargasRFIDReaderWriter.performReadReservedMemory();
			} catch (ReaderException e) {

			    e.printStackTrace();
			}
			event.setAccessPw(reservedMemoryData.get("AccessPassword"));
		    } else {
			Map<String, String> reservedMemoryData = null;
			try {
			    reservedMemoryData = SargasRFIDReaderWriter.performReadReservedMemory();
			} catch (ReaderException e) {

			    e.printStackTrace();
			}
			event.setAccessPw(reservedMemoryData.get("AccessPassword"));
		    }
		    if (event.getKillPwLock().equalsIgnoreCase("locked") || event.getKillPwLock().equalsIgnoreCase("permenant")) {
			Map<String, String> reservedMemoryData = null;
			try {
			    reservedMemoryData = SargasRFIDReaderWriter.performReadReservedMemory();
			} catch (ReaderException e) {

			    e.printStackTrace();
			}
			event.setKillPw(reservedMemoryData.get("KillPassword"));
		    } else {
			Map<String, String> reservedMemoryData = null;
			try {
			    reservedMemoryData = SargasRFIDReaderWriter.performReadReservedMemory();
			} catch (ReaderException e) {

			    e.printStackTrace();
			}
			event.setKillPw(reservedMemoryData.get("KillPassword"));
		    }

		    // read User Memory
		    byte length = 0;
		    TagFilter filter = new TagData(d.getData());
		    TagOp op = new Gen2.ReadData(Gen2.Bank.USER, 0, length);
		    short[] userMemory = null;
		    try {
			userMemory = SargasRFIDReaderWriter.performReadUserMemoryOrTID(filter, op, readAntennaNo);
		    } catch (ReaderException e) {
			driverErrorHandler.getTagDriverError(20010, e.getStackTrace());
			tagDataWriteMemoryStatus.put("UserMemory", e.getMessage());
			tagDataStatus.setTagDataMemoryStatus(tagDataWriteMemoryStatus);
		    }
		    if (userMemory != null && 0 < userMemory.length) {
			event.setUserMemory(
				ReaderUtil.byteArrayToHexString(ReaderUtil.convertShortArraytoByteArray(userMemory)));
		    }

		    // read TID info
		    TagOp tagOp = new Gen2.ReadData(Gen2.Bank.TID, 0, length);
		    short[] tidMemory = null;
		    try {
			tidMemory = SargasRFIDReaderWriter.performReadUserMemoryOrTID(filter, tagOp, readAntennaNo);
		    } catch (ReaderException e) {
			driverErrorHandler.getTagDriverError(20016, e.getStackTrace());
			tagDataWriteMemoryStatus.put("TID", e.getMessage());
			tagDataStatus.setTagDataMemoryStatus(tagDataWriteMemoryStatus);
		    }
		    if (tidMemory != null && 0 < tidMemory.length) {
			event.setTidData(
				ReaderUtil.byteArrayToHexString(ReaderUtil.convertShortArraytoByteArray(tidMemory)));
		    }
		}
		watch.stop();
		log.info(watch.shortSummary());
	    }

	    else {
		tagDataStatus.setStatus(false);
	    }
	}

	return tagDataStatus;
    }

    public TagDataStatus write() throws DriverEventException {
	StopWatch watch = new StopWatch();
	watch.start("Write Processing Tag");

	TagDataStatus tagDataStatus = new TagDataStatus();
	Map<String, String> tagDataWriteMemoryStatus = new HashMap<>();
	if (event == null) {
	    driverErrorHandler.getTagDriverError(20006, null);
	    throw new DriverEventException("Event not passed");
	}

	try {
	    // writeEPC
	    SargasRFIDReaderWriter.writeTag(event.getNewEPC(), writeAntennaNo);
	} catch (Exception e) {
	    driverErrorHandler.getTagDriverError(20007, e.getStackTrace());
	    tagDataStatus.setStatus(false);
	}

	if (tagDataStatus.isStatus()) {
	    try {
		// write Access Password
		SargasRFIDReaderWriter.tagWriteAccessPw(event.getAccessPw());
	    } catch (ReaderException e1) {
		driverErrorHandler.getTagDriverError(20009, e1.getStackTrace());
		tagDataWriteMemoryStatus.put("Access", e1.getMessage());
		tagDataStatus.setTagDataMemoryStatus(tagDataWriteMemoryStatus);
	    }

	    try {
		// write Kill Password
		SargasRFIDReaderWriter.tagWriteKillPw(event.getKillPw());
	    } catch (ReaderException e1) {
		driverErrorHandler.getTagDriverError(20008, e1.getStackTrace());
		tagDataWriteMemoryStatus.put("Kill", e1.getMessage());
		tagDataStatus.setTagDataMemoryStatus(tagDataWriteMemoryStatus);
	    }
	    try {
		// write UserMemory
		String userMom = event.getUserMemory();
		final short index1 = Short.parseShort(userMom.substring(0, 3));
		final short index2 = Short.parseShort(userMom.substring(4, 7));
		final short userMemory[] = { (short) index1, (short) index2 };
		SargasRFIDReaderWriter.writeUserMemory(userMemory, writeAntennaNo);
	    } catch (ReaderException e) {
		driverErrorHandler.getTagDriverError(20010, e.getStackTrace());
		tagDataWriteMemoryStatus.put("UserMemory", e.getMessage());
		tagDataStatus.setTagDataMemoryStatus(tagDataWriteMemoryStatus);
	    }
	    try {
		// locking the memory for EPC
		if (!StringUtils.isEmpty(event.getNewEPCLock())) {
		    SargasRFIDReaderWriter.tagLock(event.getAccessPw(),
			    TagLockOperation.EPC.execute(event.getNewEPCLock()));
		}
		// locking the memory for Access PW
		if (!StringUtils.isEmpty(event.getAccessPwLock())) {
		    SargasRFIDReaderWriter.tagLock(event.getAccessPw(),
			    TagLockOperation.ACCESS.execute(event.getAccessPwLock()));
		}
		// locking the memory for Kill PW
		if (!StringUtils.isEmpty(event.getKillPwLock())) {
		    SargasRFIDReaderWriter.tagLock(event.getAccessPw(),
			    TagLockOperation.KILL.execute(event.getKillPwLock()));
		}
		// locking the memory for User Memory
		if (!StringUtils.isEmpty(event.getUserMemoryLock())) {
		    SargasRFIDReaderWriter.tagLock(event.getAccessPw(),
			    TagLockOperation.USERMEMORY.execute(event.getUserMemoryLock()));
		}
	    } catch (ReaderException e2) {
		driverErrorHandler.getTagDriverError(20011, e2.getStackTrace());
	    }
	}

	watch.stop();
	log.info("write process stop:" + watch.shortSummary());
	return tagDataStatus;
    }

    public boolean writeUserMemory() {

	return true;
    }

    public boolean readUserMemory() {

	return true;
    }
}
