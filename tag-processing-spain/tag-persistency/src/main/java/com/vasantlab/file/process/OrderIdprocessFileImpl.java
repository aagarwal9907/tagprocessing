package com.vasantlab.file.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.vasantlab.data.tables.TagDataFeed;
import com.vasantlab.file.processinterface.OrderIdProcessFile;
import com.vasantlab.file.utill.FileProcessUtill;

@Component("orderIdProcessFile")
public class OrderIdprocessFileImpl implements OrderIdProcessFile {
    private static Logger log = LoggerFactory.getLogger(OrderIdprocessFileImpl.class);
    @Autowired
    private FileProcessUtill fileProcessUtill;

    @Value("${sourcePath}")
    private String sourcePath;

    @Override
    public List<TagDataFeed> getProcessFile(String fileName) throws FileNotFoundException {
	log.info("started processing for {}", fileName);
	StopWatch watch = new StopWatch();
	watch.start("Processing File");
	String path = null;
	String fullFileName = null;
	path = sourcePath;

	File[] files = new File(path).listFiles();
	// If this pathname does not denote a directory, then listFiles()
	// returns null.

	for (File file : files) {
	    if (file.isFile() && file.exists()) {
		fullFileName = (file.getName().contains(fileName)) ? file.getName() : null;
		if (fullFileName != null) {
		    break;
		}
	    }
	}
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// since none of the files in the folder matches the expected order number so
	// stopping processing and throwing exception to report on
	// user interface
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	if (fullFileName == null) {
	    throw new FileNotFoundException(new StringBuilder("No File Found with name :").append(fileName).toString());
	}

	List<TagDataFeed> tagData = new ArrayList<>();
	Workbook workbook = null;
	try {
	    String XLSX_FILE_PATH = path + fullFileName;

	    FileInputStream fileInputStream = new FileInputStream(new File(XLSX_FILE_PATH));

	    // Create Workbook instance holding .xls file
	    workbook = fileProcessUtill.getWorkbook(fileInputStream, XLSX_FILE_PATH);
	    int sheetno = workbook.getNumberOfSheets();
	    for (int x = 0; x < sheetno; x++) {
		// System.out.println(workbook.getNumberOfSheets());
		// Get the first worksheet
		Sheet sheet = workbook.getSheetAt(x);
		// String sheetname = workbook.getSheetName(x);

		// Iterate through each rows

		Iterator<Row> rowIterator = sheet.iterator();
		String columnVal = null;
		while (rowIterator.hasNext()) {
		    // Get Each Row
		    Row row = rowIterator.next();
		    // Leaving the first row alone as it is header
		    if (row.getRowNum() == 0)
			continue;
		    // Iterating through Each column of Each Row
		    Iterator<Cell> cellIterator = row.cellIterator();
		    TagDataFeed tagDataFeed = new TagDataFeed();

		    while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			if (row.getRowNum() == 1 & cell.getColumnIndex() >= 6) {
			    String column = cell.getStringCellValue();
			    if (cell.getColumnIndex() <= 10) {
				if (cell.getColumnIndex() == 6) {
				    columnVal = column + ";";
				} else if (cell.getColumnIndex() == 10) {
				    columnVal = columnVal + column;
				    // tagDataFeed.setColumnVal(columnVal);

				} else {
				    columnVal = columnVal + column + ";";
				}
			    }
			}
			if (row.getRowNum() >= 2) {
			    if (cell.getColumnIndex() == 1) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				tagDataFeed.setIOS_NO(cell.getStringCellValue());
			    } else if (cell.getColumnIndex() == 2) {

				tagDataFeed.setUnique_code_name(cell.getStringCellValue());
			    } else if (cell.getColumnIndex() == 3) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				tagDataFeed.setBarcode_details(cell.getStringCellValue());
			    } else if (cell.getColumnIndex() == 4) {

				tagDataFeed.setFile_name(cell.getStringCellValue());
			    } else if (cell.getColumnIndex() == 5) {

				tagDataFeed.setFile_path(cell.getStringCellValue());
			    } else if (cell.getColumnIndex() == 6) {

				tagDataFeed.setEpc_code(cell.getStringCellValue());
			    } else if (cell.getColumnIndex() == 7) {

				tagDataFeed.setUser_memory(cell.getStringCellValue());
			    } else if (cell.getColumnIndex() == 8) {

				tagDataFeed.setAccess_password(cell.getStringCellValue());
			    } else if (cell.getColumnIndex() == 9) {

				tagDataFeed.setKill_password(cell.getStringCellValue());
			    } else if (cell.getColumnIndex() == 10) {

				tagDataFeed.setTag_kill_bit(cell.getStringCellValue());
				tagDataFeed.setColumnVal(columnVal);
			    }
			}
		    }
		    if (tagDataFeed.getIOS_NO() != null) {
			tagData.add(tagDataFeed);
		    }
		}
	    }
	} catch (IOException ie) {
	    ie.printStackTrace();
	} finally {
	    log.info("Ended processing for {}", fileName);

	}
	watch.stop();
	log.info(watch.shortSummary());
	return tagData;
    }

}
