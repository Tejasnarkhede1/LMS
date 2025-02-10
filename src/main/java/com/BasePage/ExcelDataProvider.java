package com.BasePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelDataProvider {

		XSSFWorkbook workb;
		public void getData() throws IOException {
			String excelpath = System.getProperty("user.dir")+"\\TestData\\abc.xlsx";
			//String excelpath1="C:/Users/sandi/eclipse-workspace/latestversion/Aug23Sept23Hybridframework/TestData/abc.xlsx";
		
			File src = new File(excelpath);
			
			FileInputStream file = new FileInputStream(src);
			workb= new XSSFWorkbook(file);
			
		}
		
		public String getStringData(String sheetname, int row,int column) {
			
			
			return workb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
			
		}
		
		public double getNumericData(String sheetname, int row,int column) {
		return workb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		}
	}

