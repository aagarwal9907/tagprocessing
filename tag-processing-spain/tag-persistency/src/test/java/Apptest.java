import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vasantlab.data.tables.TagDataFeed;

public class Apptest {

	public static void main(String[] args) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(new File("D:\\projects\\\\vasant-lab\\\\tag-processing-spain\\rd144258.xls"));

		// Create Workbook instance holding .xls file
		Workbook workbook  = new HSSFWorkbook(fileInputStream);
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
				TagDataFeed tagDataFeed = new TagDataFeed();
				String columnVal= null;
				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();		
					if(row.getRowNum()==1 & cell.getColumnIndex()>= 6){
						String column=cell.getStringCellValue();
						if(cell.getColumnIndex()<=10){
							if(cell.getColumnIndex()==6){
								columnVal=column+",";
							}else if(cell.getColumnIndex()==10){
								columnVal=columnVal+column;
							}else{
							columnVal=columnVal+column+",";
							}
						}
					}
					if(row.getRowNum()>=2){
						if(cell.getColumnIndex()==1){
						System.out.println(cell.getNumericCellValue());
						tagDataFeed.setIOS_NO(String.valueOf(cell.getNumericCellValue()));
						}else if(cell.getColumnIndex()==2){
						System.out.println(cell.getStringCellValue());
						tagDataFeed.setUnique_code_name(cell.getStringCellValue());
						}else if(cell.getColumnIndex()==3){
							System.out.println(cell.getNumericCellValue());
							tagDataFeed.setBarcode_details(String.valueOf(cell.getNumericCellValue()));
						}else if(cell.getColumnIndex()==4){
							System.out.println(cell.getStringCellValue());
							tagDataFeed.setFile_name(cell.getStringCellValue());
						}else if(cell.getColumnIndex()==5){
							System.out.println(cell.getStringCellValue());
							tagDataFeed.setFile_path(cell.getStringCellValue());
						}else if(cell.getColumnIndex()==6){
							System.out.println(cell.getStringCellValue());
							tagDataFeed.setEpc_code(cell.getStringCellValue());
						}else if(cell.getColumnIndex()==7){
							System.out.println(cell.getStringCellValue());
							tagDataFeed.setUser_memory(cell.getStringCellValue());
						}else if(cell.getColumnIndex()==8){
							System.out.println(cell.getStringCellValue());
							tagDataFeed.setAccess_password(cell.getStringCellValue());
						}else if(cell.getColumnIndex()==9){
							System.out.println(cell.getStringCellValue());
							tagDataFeed.setKill_password(cell.getStringCellValue());
						}else if(cell.getColumnIndex()==10){
							System.out.println(cell.getStringCellValue());
							tagDataFeed.setTag_kill_bit(cell.getStringCellValue());
						}
					}
					//System.out.println(cell.getStringCellValue());
				//	System.out.println(columnVal);
					//++columnIndex;
					
				/*	if (cell.getStringCellValue().length() != 0) {

						if (columnIndex == Integer.valueOf(cols[0])) {
							oid.setOrderID(cell.getStringCellValue());

						} else if (columnIndex == Integer.valueOf(cols[1])) {
							oid.setBARCODE(cell.getStringCellValue());

						} else if (columnIndex == Integer.valueOf(cols[2])) {
							oid.setLineQuantity(cell.getStringCellValue());

						} else if (columnIndex == Integer.valueOf(cols[3])) {
							oid.setProdCode(cell.getStringCellValue());

						} else if (columnIndex == Integer.valueOf(cols[4])) {
							oid.setProdName(cell.getStringCellValue());

						} else if (columnIndex == Integer.valueOf(cols[5])) {
							oid.setRFDETAILS(cell.getStringCellValue());

						}
					}*/

	}

			}
			
		}
	}
}
