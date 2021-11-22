package com.vasantlab.tag.event;

import com.thingmagic.Gen2.LockAction;
import com.thingmagic.TagOp;

import java.util.EnumSet;
import java.util.Map;

import com.thingmagic.Gen2;
import com.thingmagic.ReaderUtil;
import com.thingmagic.TagData;
import com.thingmagic.TagFilter;
import com.thingmagic.TagReadData;
import com.vasantlab.tag.model.SargasDriver;
import com.vasantlab.tag.operation.RFIDDriverOperate;
import com.vasantlab.tag.service.SargasDriverRead;
import com.vasantlab.tag.service.SargasDriverWrite;
import com.vasantlab.tag.util.SargasRFIDReaderWriter;

public class TestMain {

	public static void main(String[] args) {

		int[] antenna = {1};
		try {

			SargasRFIDReaderWriter.initialize("tmr://192.168.1.17", antenna);
			SargasRFIDReaderWriter.connect();
			SargasRFIDReaderWriter.setupRegionAndDetectAntenna();
			TagReadData[] data = SargasRFIDReaderWriter.read();
			System.out.println(data[0].epcString());
			//SargasRFIDReaderWriter.tagWriteAccessPw("e6c5453e");
			//SargasRFIDReaderWriter.writeTag("303965C5008B9DC000000003", 0);
			//SargasRFIDReaderWriter.tagLock("e6c5453e",Gen2.LockAction.ACCESS_UNLOCK);
			//SargasRFIDReaderWriter.tagLock("e6c5453e",Gen2.LockAction.EPC_UNLOCK);
			//SargasRFIDReaderWriter.tagLock("e6c5453e",Gen2.LockAction.KILL_UNLOCK);
			//SargasRFIDReaderWriter.tagLock("e6c5453e",Gen2.LockAction.USER_UNLOCK);
			/*
			
			SargasRFIDReaderWriter.tagWriteKillPw("4C6368B4");
			
			short writeData[] = { (short) 0x0000, (short) 0x0092 };
			SargasRFIDReaderWriter.writeUserMemory(writeData, 0);*/
			
			//SargasRFIDReaderWriter.tagWriteAccessPw("e6c5454e");
			//SargasRFIDReaderWriter.tagLock("e6c5453e",Gen2.LockAction.ACCESS_UNLOCK);
			//SargasRFIDReaderWriter.tagLock("e6c5454e",Gen2.LockAction.EPC_PERMAUNLOCK);
			//SargasRFIDReaderWriter.tagLock("e6c5453e",Gen2.LockAction.KILL_UNLOCK);
			//SargasRFIDReaderWriter.tagLock("e6c5453e",Gen2.LockAction.USER_UNLOCK);
			/*
			 * byte length = 0; TagOp op1 = new Gen2.ReadData(Gen2.Bank.RESERVED, 0,
			 * length); Map<String,String>
			 * rm=SargasRFIDReaderWriter.performReadReservedMemory();
			 * //System.out.println("KillPassword"+String.format("%04X",rm.get(
			 * "KillPassword"))); System.out.println(rm.get("AccessPassword"));
			 * 
			 * 
			 * 
			  TagFilter filter = new TagData(data[0].getData()); TagOp op = new
			  Gen2.ReadData(Gen2.Bank.TID, 0, length); short[] data1 =
			 SargasRFIDReaderWriter.performReadUserMemoryOrTID(filter, op,antenna[1]); if
			 (0 < data1.length) { System.out.println(" User memory:" +
			  ReaderUtil.byteArrayToHexString(ReaderUtil.convertShortArraytoByteArray(data1
			  ))); System.out.println(" Standalone read data length:" + data1.length);
			  
			 * } TagOp op2 = new Gen2.ReadData(Gen2.Bank.USER, 0, length); short[] data2 =
			 * SargasRFIDReaderWriter.performReadUserMemoryOrTID(filter, op2,antenna[1]); if
			 * (0 < data2.length) { System.out.println(" User memory:" +
			 * ReaderUtil.byteArrayToHexString(ReaderUtil.convertShortArraytoByteArray(data2
			 * ))); System.out.println(" Standalone read data length:" + data2.length);
			 * 
			 * } TagReadData tr = SargasRFIDReaderWriter.performReadAllEPCData(filter, op);
			 * if (0 < tr.getData().length) { System.out.println("RSSI :" + tr.getRssi());
			 * System.out.println("Read Count:" + tr.getReadCount());
			 * System.out.println("Time :" + tr.getTime()); }
			 */			
			
			 
			//SargasRFIDReaderWriter.performReadUserMemoryOrTID();
			// SargasRFIDReaderWriter.performReadKillPassword(filter, op);
			// SargasRFIDReaderWriter.performReadAllMemOperation(filter, op);
			// SargasRFIDReaderWriter.tagLock(writeData, LockAction.USER_LOCK);
			// System.out.println(data1);
			// 303965EAC27159000000009C
			/*
			 * RFIDDriverOperate operation = new RFIDDriverOperate();
			 * SargasDriver sgDriver = new SargasDriver(); SargasDriverRead read
			 * = new SargasDriverRead(sgDriver);
			 * operation.executeOperation(read); //
			 * 
			 * sgDriver.getEvent().setNewEPC("06175377444246461234543A");
			 * SargasDriverWrite writer = new SargasDriverWrite(sgDriver);
			 * operation.executeOperation(writer);
			 */
			byte length = 0;
			 TagFilter filter = new TagData(data[0].getData());
			 TagOp op = new Gen2.ReadData(Gen2.Bank.TID, 0, length); 
			 short[] data1 = SargasRFIDReaderWriter.performReadUserMemoryOrTID(filter, op,0);
			 if(0 < data1.length)
			 { 
				 System.out.println(" User memory:" +ReaderUtil.byteArrayToHexString(ReaderUtil.convertShortArraytoByteArray(data1))); 
			     System.out.println(" Standalone read data length:" + data1.length);
			 }
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

}
