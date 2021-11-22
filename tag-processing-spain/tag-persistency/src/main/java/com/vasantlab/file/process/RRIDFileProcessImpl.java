package com.vasantlab.file.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vasanthlab.dao.data.service.interfaces.TagDataFeedService;
import com.vasantlab.data.tables.RFIDData;
import com.vasantlab.data.tables.TagDataFeed;
import com.vasantlab.exception.FileProcessingException;
import com.vasantlab.file.processinterface.RFIDProcessFile;
import com.vasantlab.file.utill.FileProcessUtill;

@Component("rfidProcessFile")
public class RRIDFileProcessImpl implements RFIDProcessFile {
    private static Logger log = LoggerFactory.getLogger(RRIDFileProcessImpl.class);
    @Autowired
    TagDataFeedService tagDataFeedService;

    @Autowired
    private FileProcessUtill fileProcessUtill;

    @Override
    public List<RFIDData> getRFIDProcessFile(TagDataFeed data) throws FileProcessingException {

	String config = data.getColumnVal();
	String[] cols = config.split(";");
	List<RFIDData> rfidData = new ArrayList<>();

	try {
	    FileInputStream fileInputStream = new FileInputStream(new File(data.getFile_path()));
	    rfidData = createWorkbook(fileInputStream, rfidData, data.getFile_path(), cols);
	} catch (FileNotFoundException e) {
	    throw new FileProcessingException(e.getLocalizedMessage());
	}

	log.info(rfidData.size() + " No of Records Read");
	rfidData.forEach(d -> d.setOrderId(data.getIOS_NO()));
	return rfidData;

    }

    private List<RFIDData> createWorkbook(FileInputStream fileInputStream, List<RFIDData> rfidData, String filePath,
	    String[] cols) throws FileProcessingException {
	Workbook workbook = null;
	try {
	    workbook = fileProcessUtill.getWorkbook(fileInputStream, filePath);
	} catch (IOException e) {
	    throw new FileProcessingException(e.getLocalizedMessage());
	}
	int sheetno = workbook.getNumberOfSheets();
	for (int x = 0; x < sheetno; x++) {
	    // Get the first worksheet
	    Sheet sheet = workbook.getSheetAt(x);
	    // Iterate through each rows

	    Iterator<Row> rowIterator = sheet.iterator();

	    while (rowIterator.hasNext()) {
		// Get Each Row
		Row row = rowIterator.next();
		// Leaving the first row alone as it is header
		if (row.getRowNum() == 0)
		    continue;
		// Iterating through Each column of Each Row
		Iterator<Cell> cellIterator = row.cellIterator();

		RFIDData rfidDataFeed = new RFIDData();

		while (cellIterator.hasNext()) {
		    Cell cell = cellIterator.next();
		    int columnIndex = cell.getColumnIndex();
		    if (cell.getStringCellValue().length() != 0 && !cell.getStringCellValue().equals("")) {

			if (columnIndex == 1) {
			    rfidDataFeed.setBarcode(cell.getStringCellValue());
			}
			if (columnIndex == CellReference.convertColStringToIndex(cols[0])) {
			    rfidDataFeed.setEpc(cell.getStringCellValue().toString());

			} else if (columnIndex == CellReference.convertColStringToIndex(cols[1])) {
			    rfidDataFeed.setUser_Memory(cell.getStringCellValue());

			} else if (columnIndex == CellReference.convertColStringToIndex(cols[2])) {
			    rfidDataFeed.setAccess_Password(cell.getStringCellValue());

			} else if (columnIndex == CellReference.convertColStringToIndex(cols[3])) {
			    rfidDataFeed.setKill_Password(cell.getStringCellValue());

			}
		    }
		}
		// Skipping adding blank row since in that case the EPC would be empty as in
		// excel it is coming as " "
		if (!rfidDataFeed.getEpc().trim().equals("")) {
		    rfidData.add(rfidDataFeed);
		}

	    }
	}
	return rfidData;
    }
}
