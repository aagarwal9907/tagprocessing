package com.vasantlab.tag.processingservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vasanthlab.dao.data.service.interfaces.RFIDDataService;
import com.vasanthlab.dao.data.service.interfaces.TagDataFeedService;
import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.file.process.RRIDFileProcessImpl;
import com.vasantlab.tag.processingservice.interfaces.RFIDFileProcessService;

@Component
public class RFIDFileProcessServiceImpl implements RFIDFileProcessService{
	@Autowired
	TagDataFeedService tagDataFeedService;
	@Autowired
	RRIDFileProcessImpl rfidProcessFile;
	@Autowired
	RFIDDataService rfidDataService;
	@Override
	public String tagDataPersists(String orderId, String barcode) {
		String result = null;
		String XLSX_FILE_PATH = tagDataFeedService.findFilePath(orderId, barcode);
		if(!XLSX_FILE_PATH.isEmpty()){
			
			//List<RFIDData> rfidData = rfidProcessFile.getRFIDProcessFile(orderId, barcode, XLSX_FILE_PATH);
			//rfidDataService.saveFileRecords(rfidData);
			
		}else{
			result = "Problem with file";
		}
		
		
		return result;
	}

}
