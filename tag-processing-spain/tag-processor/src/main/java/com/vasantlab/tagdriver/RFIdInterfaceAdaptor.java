package com.vasantlab.tagdriver;

import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.data.tables.TagDataFeed;
import com.vasantlab.model.ProcessingContext;

public interface RFIdInterfaceAdaptor {
	public ProcessingContext tagRead();

	public ProcessingContext tagWrite(RFIDData rfDetails,TagDataFeed tagDataFeed);

}
