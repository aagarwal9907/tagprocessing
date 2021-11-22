package com.vasantlab.util;

import java.util.List;

import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.data.tables.TagDataFeed;
import com.vasantlab.tagdriver.RFIdInterfaceAdaptor;

public final class CachingUtil {
	

	  private CachingUtil() {
	    throw new IllegalStateException("Utility class");
	  }

	  public static  Caching<String, String> caching = new Caching<>(9);
	  public static  Caching<String, List<RFIDData>> cachingRFIDDatalist = new Caching<>(1);
	  public static  Caching<String,TagDataFeed> cachingFeedData = new Caching<>(1);
	  public static  Caching<String, char[]> cachingReceiveByte = new Caching<>(1);
	  public static  Caching<String,RFIDData> cachingProcessRFIDData = new Caching<>(1);
	  public static  Caching<String, RFIdInterfaceAdaptor> cachingRFIdInterfaceAdaptor = new Caching<>(1);
	  public static  Caching<String,String> cachingErrorCode = new Caching<>(1);
	  public static  Caching<String,String> cachingError04Command = new Caching<>(1);
	  public static  Caching<String,String> cachingUserMemoryException = new Caching<>(1);
	
	public static void clearCachingData() {
		cachingRFIDDatalist.clear();
		caching.clear();
		cachingFeedData.clear();
		cachingReceiveByte.clear();
		cachingErrorCode.clear();
	}

}
