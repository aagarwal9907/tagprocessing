package com.vasantlab.file.process;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vasanthlab.dao.data.service.interfaces.RFIDDataService;
import com.vasanthlab.dao.data.service.interfaces.TagDataFeedService;
import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.data.tables.TagDataFeed;
import com.vasantlab.file.processinterface.RFIDReportProcessFile;
import com.vasantlab.model.ErrorCode;
import com.vasantlab.model.ReportGenerationStatus;

@Component("rfidReportProcessFile")
public class RFIDReportProcessFileImpl implements RFIDReportProcessFile {

    private static Logger log = LoggerFactory.getLogger(RFIDReportProcessFileImpl.class);

    @Value("${tag.report.dir}")
    private String reportDir;
    @Autowired
    private TagDataFeedService tagDataFeedService;
    @Autowired
    private RFIDDataService rfidDataService;

    private static String[] columns = { "BarCode", "Epc", "RSSI", "ReadCount", "States", "AccessPassword",
	    "KillPassWord", "TID", "UserMemory", "Tag Creation Date", "EPCLockInfo", "KillPwLockInfo",
	    "AccessPwLockInfo", "UserMemoryLockInfo", "TagError" };

    @Override
    public ReportGenerationStatus getReportFileCreation(String orderId, String barCode) {
	TagDataFeed tagData = tagDataFeedService.findTagData(orderId, barCode);
	List<RFIDData> tagRfidData = rfidDataService.finddata(orderId, barCode);
	// Create a Workbook
	Workbook workbook = new XSSFWorkbook();
	CreationHelper createHelper = workbook.getCreationHelper();

	// Create a Sheet
	Sheet sheet = workbook.createSheet(orderId + "_" + barCode);

	// Create a Font for styling header cells
	Font headerFont = workbook.createFont();
	headerFont.setFontHeightInPoints((short) 14);
	headerFont.setColor(IndexedColors.BLUE.getIndex());

	// Create a CellStyle with the font
	CellStyle headerCellStyle = workbook.createCellStyle();
	headerCellStyle.setFont(headerFont);

	// Create a Row
	Row headerRow = sheet.createRow(0);
	// Create cells
	for (int i = 0; i < columns.length; i++) {
	    Cell cell = headerRow.createCell(i);
	    cell.setCellValue(columns[i]);
	    cell.setCellStyle(headerCellStyle);
	}

	// Create Cell Style for formatting Date
	CellStyle dateCellStyle = workbook.createCellStyle();
	dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

	// Create Other rows and cells with employees data
	int rowNum = 1;

	for (RFIDData rfidData : tagRfidData) {
	    Row row = sheet.createRow(rowNum++);

	    row.createCell(0).setCellValue(rfidData.getBarcode());
	    row.createCell(1).setCellValue(rfidData.getEpc());
	    row.createCell(2).setCellValue(rfidData.getRssi());
	    row.createCell(3).setCellValue(rfidData.getCount());
	    row.createCell(4).setCellValue(rfidData.getStatus());
	    row.createCell(5).setCellValue(rfidData.getAccess_Password());
	    row.createCell(6).setCellValue(rfidData.getKill_Password());
	    row.createCell(7).setCellValue(rfidData.getTidData());
	    row.createCell(8).setCellValue(rfidData.getUser_Memory());
	    Cell dateOfBirthCell = row.createCell(9);
	    dateOfBirthCell.setCellValue(rfidData.getDate());
	    dateOfBirthCell.setCellStyle(dateCellStyle);
	    row.createCell(10).setCellValue(tagData.getEpc_code());
	    row.createCell(11).setCellValue(tagData.getKill_password());
	    row.createCell(12).setCellValue(tagData.getAccess_password());
	    row.createCell(13).setCellValue(tagData.getUser_memory());
	    row.createCell(14).setCellValue(rfidData.getMemory_rw_error());

	}

	// Resize all columns to fit the content size
	for (int i = 0; i < columns.length; i++) {
	    sheet.autoSizeColumn(i);
	}
	StringBuilder fileName = new StringBuilder();
	fileName.append(reportDir).append(orderId).append(barCode).append(".xlsx");
	log.info("Report is being written to the file: {}", fileName);
	// Write the output to a file
	FileOutputStream fileOut = null;
	ReportGenerationStatus status = new ReportGenerationStatus();
	try {
	    fileOut = new FileOutputStream(fileName.toString());
	    workbook.write(fileOut);
	    fileOut.close();
	} catch (IOException e2) {
	    status.setErrorCode(ErrorCode.ERROR);
	    status.setMessage(e2.getLocalizedMessage());
	    log.error("Report failed to write on file system", e2);
	}
	log.info("Report Genrated for {} and {}", orderId, barCode);
	return status;
    }

}
