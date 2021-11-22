package com.vasantlab.file.processinterface;


import java.io.FileNotFoundException;
import java.util.List;

import com.vasantlab.data.tables.TagDataFeed;

public interface OrderIdProcessFile {
	public  List<TagDataFeed> getProcessFile(String fileName) throws FileNotFoundException;
}
