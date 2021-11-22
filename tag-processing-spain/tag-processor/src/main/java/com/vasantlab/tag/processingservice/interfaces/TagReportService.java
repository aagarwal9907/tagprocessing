package com.vasantlab.tag.processingservice.interfaces;

import com.vasantlab.model.ReportGenerationStatus;
import com.vasantlab.model.TagProcessingInput;

public interface TagReportService {
	public ReportGenerationStatus reportProcessing(TagProcessingInput tagProcessingInput);
}
