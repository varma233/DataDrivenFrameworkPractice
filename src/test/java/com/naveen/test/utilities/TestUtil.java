package com.naveen.test.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.naveen.test.base.TestBase;

public class TestUtil extends TestBase{
	
	public static int fileNumber = 0;
	static String screenshotPathTestNG = rootDirectory+"\\target\\surefire-reports\\html\\";
	static String screenshotPathExtentReport = rootDirectory+"\\target\\extentreport\\";
	static String screenshotfileNameTestNG; 
	static String screenshotfileNameExtentReport;
	
	public static void caputureScreenShot() throws IOException{
		fileNumber++;
		screenshotfileNameTestNG = screenshotPathTestNG+"\\screenshot_"+fileNumber+".jpg";
		screenshotfileNameExtentReport = screenshotPathExtentReport+"\\screenshot_"+fileNumber+".jpg";
		
		File screenshotFIle = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFIle, new File(screenshotfileNameTestNG));
		FileUtils.copyFile(screenshotFIle, new File(screenshotfileNameExtentReport));
	}

}
