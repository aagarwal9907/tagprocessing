package com.vasantlab.file.processinterface;

import com.vasantlab.model.ReportGenerationStatus;

public interface RFIDReportProcessFile {
	public ReportGenerationStatus getReportFileCreation(String orderId, String barCode);
}
