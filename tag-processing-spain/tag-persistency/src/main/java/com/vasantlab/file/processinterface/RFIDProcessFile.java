package com.vasantlab.file.processinterface;

import java.util.List;

import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.data.tables.TagDataFeed;
import com.vasantlab.exception.FileProcessingException;

public interface RFIDProcessFile {
	public List<RFIDData> getRFIDProcessFile(TagDataFeed data) throws FileProcessingException;
}
