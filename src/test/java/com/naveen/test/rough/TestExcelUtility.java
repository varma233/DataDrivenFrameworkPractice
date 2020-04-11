package com.naveen.test.rough;

import com.naveen.test.utilities.ExcelReader;

public class TestExcelUtility {

	public static void main(String[] args) {
		ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\TestData.xlsx");
		String sheetName = "Sheet1";
		
		int rowCount = excel.getRowCount(sheetName);
		int colCount = excel.getColumnCount(sheetName);
		
		if(rowCount > 0 ) {
			for(int row=2 ; row <= rowCount; row++) {			
				for(int col=0; col < colCount; col++) {
					System.out.print(excel.getCellData(sheetName, col, row)+"\t");
				}
				System.out.println();
			}
		}
		
	}

}
