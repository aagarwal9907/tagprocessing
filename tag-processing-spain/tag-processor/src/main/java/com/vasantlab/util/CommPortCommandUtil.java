package com.vasantlab.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommPortCommandUtil {
	private static Logger log = LoggerFactory.getLogger(CommPortCommandUtil.class);
	public static final  byte[] STARTOKCOMMAND = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02,
			(byte) 0x31, (byte) 0x30, (byte) 0x30 };

	public static final  byte[] WRITEOKCOMMAND = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02,
			(byte) 0x03, (byte) 0x30, (byte) 0x30 };

	public static final  byte[] READOKCOMMAND = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02,
			(byte) 0x05, (byte) 0x30, (byte) 0x30 };

	public static final  byte[] READEFAILCOMMAND = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x02,
			(byte) 0x06, (byte) 0x30, (byte) 0x30 };

	public static final  byte[] ERRORCODECOMMAND = new byte[] { (byte) 0xAA, (byte) 0xAA, (byte) 0x24, (byte) 0x03,
			(byte) 0x90, (byte) 0x00, (byte) 0x30, (byte) 0x30 };

	public static final  String WRITEREPEAT = "AAAA2402023030";
	
	public  static final  byte[] READ_PT1=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x81,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_PT2=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x82,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_PT3=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x83,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_PT4=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x84,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_PT5=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x85,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_PT6=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x86,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_PT7=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x87,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M11= new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x88,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M21=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x89,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M31=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x8A,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M12=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x8B,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M22=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x8C,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M32=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0x8D,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M13=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0xB0,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M23=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0xB1,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M33=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0xB2,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M14=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0xB3,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M24=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0xB4,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_M34=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0xB5,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_DP1=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0xB6,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_DP2=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0xB7,(byte)0x30,(byte)0x30};
	public static final  byte[] READ_DP3=new byte[]{(byte)0xAA,(byte)0xAA,(byte)0x24,(byte)0x02,(byte)0xB8,(byte)0x30,(byte)0x30};
	
	
	public static final String ERRORSTARTCOMMAND = "AAAA24023031";
	public static final String ERRORREADCOMMAND =  "AAAA24020433";
	public static final String ERRORWRITECOMMAND = "AAAA24020232";

	public static boolean getCommand(String errorCode, String recevieData) {
		boolean result = false;
		switch (errorCode.toUpperCase()) {
		case "0033":
			if (recevieData.contains(ERRORSTARTCOMMAND)) {
				CachingUtil.cachingErrorCode.clear();
				result = true;
			}
			break;
		case "0133":
			if (recevieData.contains(ERRORSTARTCOMMAND)) {
				CachingUtil.cachingErrorCode.clear();
				result = true;
			}
			break;
		case "0233":
			if (recevieData.contains(ERRORSTARTCOMMAND)) {
				CachingUtil.cachingErrorCode.clear();
				result = true;
			}
			break;
		case "0333":
			if (recevieData.contains(ERRORREADCOMMAND)) {
				CachingUtil.cachingErrorCode.clear();
				result = true;
			}
			break;
		case "0433":
			if (recevieData.contains(ERRORREADCOMMAND)) {
				CachingUtil.cachingError04Command.set("04ErrorCommand", ERRORREADCOMMAND);
				result = true;
			} else if (recevieData.contains(ERRORSTARTCOMMAND)
					&& CachingUtil.cachingError04Command.get("04ErrorCommand").contains(ERRORREADCOMMAND)) {
				CachingUtil.cachingErrorCode.clear();
				CachingUtil.cachingError04Command.clear();
				result = true;
			}
			break;
		case "0533":
			if (recevieData.contains(ERRORSTARTCOMMAND)) {
				CachingUtil.cachingErrorCode.clear();
				result = true;
			}
			break;
		case "F533":
			if (recevieData.contains(ERRORREADCOMMAND)) {
				CachingUtil.cachingErrorCode.clear();
				result = true;
			}
			break;
		case "020A":
			if (recevieData.contains(ERRORSTARTCOMMAND)) {
				CachingUtil.cachingErrorCode.clear();
				result = true;
			}
			break;
		case "030A":
			if (recevieData.contains(ERRORSTARTCOMMAND)) {
				CachingUtil.cachingErrorCode.clear();
				result = true;
			}
			break;
		case "0202":
			if (recevieData.contains(ERRORWRITECOMMAND)) {
				CachingUtil.cachingErrorCode.clear();
				result = true;
			}
			break;
		default:
			log.info("no match {},{}",errorCode,recevieData);
		}
		return result;
	}

}
