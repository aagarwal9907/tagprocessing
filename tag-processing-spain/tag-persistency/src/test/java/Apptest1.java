import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vasantlab.data.tables.RFIDData;

public class Apptest1 {

	public static void main(String[] args) throws Exception {

		FileInputStream fileInputStream = new FileInputStream(new File("D:\\projects\\vasant-lab\\tag-processing-spain\\SHUBHA.xlsx"));
		
		// Create Workbook instance holding .xls file
		Workbook workbook  = new XSSFWorkbook(fileInputStream);
		List<RFIDData> dataList=new ArrayList<>();
		int sheetno = workbook.getNumberOfSheets();
		for (int x = 0; x < sheetno; x++) {
			//System.out.println(workbook.getNumberOfSheets());
			// Get the first worksheet
			Sheet sheet = workbook.getSheetAt(x);
			//String sheetname = workbook.getSheetName(x);
			// Iterate through each rows
			int rowCount = sheet.getPhysicalNumberOfRows();
			System.out.println(rowCount);
			Iterator<Row> rowIterator = sheet.iterator();
			
			while (rowIterator.hasNext()) {
				// Get Each Row
				Row row = (Row) rowIterator.next();
				// Leaving the first row alone as it is header
				if (row.getRowNum() == 0)
					continue;
				// Iterating through Each column of Each Row
				Iterator<Cell> cellIterator = row.cellIterator();
				
				RFIDData rfidDataFeed = new RFIDData();

				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					if (cell.getStringCellValue().length() != 0) {
						if(columnIndex ==1){
							rfidDataFeed.setBarcode(cell.getStringCellValue());
							System.out.println(cell.getStringCellValue());
						}
						if (columnIndex == CellReference.convertColStringToIndex("c")) {
							rfidDataFeed.setEpc(cell.getStringCellValue());
							System.out.println(cell.getStringCellValue());

						} else if (columnIndex == CellReference.convertColStringToIndex("d")) {
							rfidDataFeed.setUser_Memory(cell.getStringCellValue());
							System.out.println(cell.getStringCellValue());

						} else if (columnIndex == CellReference.convertColStringToIndex("e")) {
							rfidDataFeed.setAccess_Password(cell.getStringCellValue());
							System.out.println(cell.getStringCellValue());

						} else if (columnIndex == CellReference.convertColStringToIndex("f")) {
							rfidDataFeed.setKill_Password(cell.getStringCellValue());
							System.out.println(cell.getStringCellValue());

						} 
				}
				}
				dataList.add(rfidDataFeed);
			}
		}
		System.out.println(dataList.toString());
	}
}
				
