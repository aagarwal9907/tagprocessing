package com.vasantlab.tag.processingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasanthlab.dao.data.service.interfaces.TagDataFeedService;
import com.vasantlab.file.processinterface.RFIDReportProcessFile;
import com.vasantlab.model.ReportGenerationStatus;
import com.vasantlab.model.TagProcessingInput;
import com.vasantlab.tag.processingservice.interfaces.TagReportService;

@Service("tagReportService")
public class TagReportServiceImpl implements TagReportService {
	
	@Autowired
	TagDataFeedService tagDataFeedService;
	
	@Autowired
	private RFIDReportProcessFile rfidReportProcessFile;

	@Override
	public ReportGenerationStatus reportProcessing(TagProcessingInput tagProcessingInput) {
		int count=tagDataFeedService.findBarcode(tagProcessingInput.getOrderId(),
				tagProcessingInput.getBarcode());
		if(count==0) {
			return ReportGenerationStatus.createNoRecordStatus();
		}else {
		return rfidReportProcessFile.getReportFileCreation(tagProcessingInput.getOrderId(),
				tagProcessingInput.getBarcode());
		}
	}

}
