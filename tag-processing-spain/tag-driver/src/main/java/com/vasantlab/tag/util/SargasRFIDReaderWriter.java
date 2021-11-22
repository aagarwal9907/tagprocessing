package com.vasantlab.tag.util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.thingmagic.Gen2;
import com.thingmagic.Gen2.TagData;
import com.vasantlab.tag.service.exception.DriverEventException;
import com.vasantlab.tag.service.exception.TagDriverError;
import com.vasantlab.tag.service.exception.TagDriverErrorHandler;
import com.thingmagic.Reader;
import com.thingmagic.ReaderException;
import com.thingmagic.SimpleReadPlan;
import com.thingmagic.TMConstants;
import com.thingmagic.TagFilter;
import com.thingmagic.TagOp;
import com.thingmagic.TagProtocol;
import com.thingmagic.TagReadData;

/**
 * This class Integrate with Mercury API of Sargas. Any new API interface would
 * need such new class.
 * 
 * @author Shubha
 *
 */
public final class SargasRFIDReaderWriter {
	
	private static final String READER_VERSION_MODEL = "/reader/version/model";

	private static final String READER_REGION_ID = "/reader/region/id";

	private static final String READER_GEN2_SESSION = "/reader/gen2/session";

	private static final String READER_READ_PLAN = "/reader/read/plan";

	private static final String READER_TAGOP_ANTENNA = "/reader/tagop/antenna";
	private SargasRFIDReaderWriter() {}

	private static int[] antennaList;

	private static Reader reader;

	private static int readTimeOut;

	private static byte readLength = 2;

	private static TagDriverErrorHandler driverErrorHandler = new TagDriverErrorHandler();
	// default timeout configuration
	public static void initialize(String uri, int[] antenna) throws Exception {
		initialize(uri, antenna, 1000);
	}

	public static void initialize(String uri, int[] antenna, int timeout) throws Exception {

		if (antenna != null && antenna.length >= 1) {
			antennaList = new int[antenna.length];
			antennaList=Arrays.copyOf(antenna, antenna.length);
			/*
			 * for (int i = 0; i < antenna.length; i++) { antennaList[i] = antenna[i]; }
			 */
		} else {
			driverErrorHandler.getTagDriverError(20000, null);
			throw new Exception("No antenna provided");
		}
		readTimeOut = timeout;
		reader = Reader.create(uri);

	}

	/*
	 * public static void initialize(String uri, int[] antenna, int timeout, boolean
	 * trace) throws Exception { initialize(uri, antenna, timeout); if (trace) {
	 * setTrace(new String[] { "on" }); } }
	 * 
	 * private static void setTrace(String args[]) { if
	 * (args[0].toLowerCase().equals("on")) {
	 * reader.addTransportListener(reader.simpleTransportListener); } }
	 */

	public static void connect() throws ReaderException {
		try {
			reader.connect();
		} catch (ReaderException e) {
			System.out.println(String.format("An Error Occured during Initialization of the driver %s::%s",TagDriverError.ERROR_20001.getErrorCode(),TagDriverError.ERROR_20001.getErrorDescription()));
			// no errors
			System.err.println("================================================================================");
			System.err.println("|The Sargas Driver failed to initialize please check if it is connected properly|");
			System.err.println("================================================================================");
			
			throw e;
		}
	}

	public static TagReadData[] read() throws ReaderException {
		
		SimpleReadPlan plan = new SimpleReadPlan(antennaList, TagProtocol.GEN2, null, null, 1000);
		reader.paramSet(TMConstants.TMR_PARAM_READ_PLAN, plan);

		// Read tags
		return reader.read(readTimeOut);

	}

	/**
	 * This method would read the user memory data area
	 * 
	 * @param TagFilter
	 *            filter
	 * @param TagOp
	 *            op.
	 * @throws ReaderException
	 */
	public static short[] performReadUserMemoryOrTID(TagFilter filter, TagOp op,int antenna) throws ReaderException {
		// Use first antenna for operation
		if (antennaList != null) {
			reader.paramSet(READER_TAGOP_ANTENNA, antennaList[antenna]);
		}

		return  (short[]) reader.executeTagOp(op, filter);
		
	}

	/**
	 * This method would read the RSSI,ReadCount,Time
	 * 
	 * @param TagFilter
	 *            filter
	 * @param TagOp
	 *            op.
	 * @throws ReaderException
	 */
	public static TagReadData performReadAllEPCData(TagFilter filter, TagOp op) throws DriverEventException,ReaderException {
		TagReadData[] tagReads = null;
		TagReadData tagReadData = null;
		SimpleReadPlan plan = new SimpleReadPlan(antennaList, TagProtocol.GEN2, filter, op, 1000);
		reader.paramSet(READER_READ_PLAN, plan);
		tagReads = reader.read(500);
		if (tagReads.length == 0) {
			driverErrorHandler.getTagDriverError(20002, null);
			throw new DriverEventException("Exception while reading All EPC Data(No tags found)");
			
		} else {
			for (TagReadData tr : tagReads) {
				tagReadData = tr;
			}
		}
		return tagReadData;
	}

	public static Map<String, String> performReadReservedMemory() throws ReaderException {
		TagReadData[] tagReads = null;
		Map<String, String> memoryData = new HashMap<>();
		Gen2.Session oldSession = (Gen2.Session) reader.paramGet(READER_GEN2_SESSION);
		Gen2.Session newSession = Gen2.Session.S0;
		System.out.println("Changing to Session " + newSession + " (from Session " + oldSession + ")");
		reader.paramSet(READER_GEN2_SESSION, newSession);
		tagReads = reader.read(500);
		TagFilter filter = tagReads[0].getTag();

		Gen2.ReadData tagop = new Gen2.ReadData(Gen2.Bank.RESERVED, 0, (byte) 2);
		try {
			short[] killPwdata = (short[]) reader.executeTagOp(tagop, filter);
			String killPw = null;
			for (short word : killPwdata) {
				if(killPw == null){
					killPw = String.format("%04X", word);
				}else if(killPw!= null){
					killPw = killPw + String.format("%04X", word);
				}	
				
			}
			memoryData.put("KillPassword", killPw);
			killPw = null;	
		} catch (ReaderException re) {
			driverErrorHandler.getTagDriverError(20003, re.getStackTrace());
		}
		tagop = new Gen2.ReadData(Gen2.Bank.RESERVED, 2, (byte) 2);
		try {
			short[] accessPw = (short[]) reader.executeTagOp(tagop, filter);
			String accessPwdata = null;
			for (short word : accessPw) {
				if (accessPwdata == null) {
					accessPwdata = String.format("%04X", word);
				} else if (accessPwdata != null) {
					accessPwdata = accessPwdata + String.format("%04X", word);
				}
				
			}
			memoryData.put("AccessPassword", accessPwdata);
			accessPwdata = null;
			
		} catch (ReaderException re) {
			driverErrorHandler.getTagDriverError(20004, re.getStackTrace());
		} finally {
			// Restore original settings
			reader.paramSet(READER_GEN2_SESSION, oldSession);
		}
		return memoryData;
	}
	/**
	 * This method would write the user memory data area
	 * 
	 * @param userData
	 *            short []
	 * @param antenna
	 *            int anntena where to write.
	 * @throws ReaderException
	 */
	public static void writeUserMemory(short[] userData, int antenna) throws ReaderException {
		reader.paramSet(READER_TAGOP_ANTENNA, antennaList[antenna]);
		Gen2.BlockWrite blockwrite = new Gen2.BlockWrite(Gen2.Bank.USER, 0, (byte) userData.length, userData);
		reader.executeTagOp(blockwrite, null);

	}

	/**
	 * This method would write the Access Password
	 * 
	 * @param accessPassword
	 *            String
	 * @throws ReaderException
	 */
	public static void tagWriteAccessPw(String accessPassword) throws ReaderException {
		reader.paramSet(TMConstants.TMR_PARAM_TAGOP_PROTOCOL, TagProtocol.GEN2);
		BigInteger pw = new BigInteger(accessPassword, 16);
		Gen2.WriteData write = new Gen2.WriteData(Gen2.Bank.getBank(0), 2,
				new short[] { (short) (pw.intValue() >> 16), (short) (pw.intValue() & 0xffff) });
		reader.executeTagOp(write, null);
	}
	/**
	 * This method would write the Kill Password
	 * 
	 * @param killPassword
	 *            String
	 * @throws ReaderException
	 */
	public static void tagWriteKillPw(String killPassword) throws ReaderException {
		reader.paramSet(TMConstants.TMR_PARAM_TAGOP_PROTOCOL, TagProtocol.GEN2);
		BigInteger pw = new BigInteger(killPassword, 16);
		Gen2.WriteData write = new Gen2.WriteData(Gen2.Bank.getBank(0), 0,
				new short[] { (short) (pw.intValue() >> 16), (short) (pw.intValue() & 0xffff) });
		reader.executeTagOp(write, null);

	}
	/**
	 * This method would write the Lock permission for Access Password, Kill
	 * Password or EPC
	 * 
	 * @param Password
	 *            String
	 * @param lock
	 *            LockAction
	 * @throws ReaderException
	 */
	public static void tagLock(String password, Gen2.LockAction lock) throws ReaderException {
		BigInteger pw = new BigInteger(password, 16);
		Gen2.Lock lock0 = new Gen2.Lock(pw.intValue(), lock);
		reader.executeTagOp(lock0, null);
	}

	

	/**
	 * @param epc
	 * @param antenna
	 * @throws Exception
	 */
	public static void writeTag(String epc, int antenna) throws Exception {
		// setupRegionAndDetectAntenna();
		reader.paramSet(READER_TAGOP_ANTENNA, antennaList[antenna]);
		Gen2.WriteTag tagop = new Gen2.WriteTag(new TagData(epc));
		reader.executeTagOp(tagop, null);
		// destroy();
	}

	public static void setupRegionAndDetectAntenna() throws ReaderException, Exception {
		if (Reader.Region.UNSPEC == (Reader.Region) reader.paramGet(READER_REGION_ID)) {
			Reader.Region[] supportedRegions = (Reader.Region[]) reader
					.paramGet(TMConstants.TMR_PARAM_REGION_SUPPORTEDREGIONS);
			if (supportedRegions.length < 1) {
				driverErrorHandler.getTagDriverError(20005,null);
				throw new Exception("Reader doesn't support any regions");
			} else {
				reader.paramSet(READER_REGION_ID, supportedRegions[0]);
			}
		}

		/**
		 * Checking the software version of Sargas. Antenna detection is
		 * supported on Sargas from the software versions higher than 5.1.x.x.
		 * User has to pass antenna as an argument, if the antenna detection is
		 * not supported on the respective reader firmware.
		 */
		String model = reader.paramGet(READER_VERSION_MODEL).toString();
		Boolean checkPort = (Boolean) reader.paramGet(TMConstants.TMR_PARAM_ANTENNA_CHECKPORT);
		String swVersion = (String) reader.paramGet(TMConstants.TMR_PARAM_VERSION_SOFTWARE);
		if ((model.equalsIgnoreCase("M6e Micro") || model.equalsIgnoreCase("M6e Nano")
				|| (model.equalsIgnoreCase("Sargas") && (swVersion.startsWith("5.1")))) && (false == checkPort)
				&& antennaList == null) {
			driverErrorHandler.getTagDriverError(20012,null);
		}

		if ("M6e".equalsIgnoreCase(model) || "M6e PRC".equalsIgnoreCase(model) || "M6e JIC".equalsIgnoreCase(model)
				|| "M6e Micro".equalsIgnoreCase(model) || "Mercury6".equalsIgnoreCase(model)
				|| "Sargas".equalsIgnoreCase(model) || "Astra-EX".equalsIgnoreCase(model)) {
			// Specifying the readLength = 0 will return full TID for any tag
			// read in case of M6e varients, M6, Astra-EX and Sargas readers.
			readLength = 0;
		} else {
			readLength = 2;
		}

	}

	public static void destroy() {
		reader.destroy();
	}

}
