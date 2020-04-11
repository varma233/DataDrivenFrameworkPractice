package com.naveen.test.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.CustomAttribute;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.naveen.test.base.TestBase;
import com.naveen.test.utilities.TestUtil;

public class CustomListener extends TestBase implements ITestListener {
	@Override
	public void onTestFailure(ITestResult result) {
		Reporter.log("<br/><b>Oops! Test case failed<b/>");	
		Markup m = MarkupHelper.createLabel("FAIL", ExtentColor.RED);
		test.fail(m);
		test.log(Status.FAIL, "Test Failed");
		
		try {
			TestUtil.caputureScreenShot();			
			Reporter.log("<br/> <a href=\""+"Screenshot_"+TestUtil.fileNumber+".jpg"+"\" target=\"_blank\">SCREENSHOT</a>");
			Reporter.log("<br/> <img src=\""+"Screenshot_"+TestUtil.fileNumber+".jpg"+"\"  height=200 width=300>");
			test.log(Status.ERROR, "Screenshot", MediaEntityBuilder.createScreenCaptureFromPath("Screenshot_"+TestUtil.fileNumber+".jpg").build());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(result.getThrowable() != null) {
			test.log(Status.FAIL, result.getThrowable());
		}
			
	}
	
	
	@Override
	public void onTestStart(ITestResult result) {
		test = report.createTest(result.getMethod().getMethodName());	
		
		CustomAttribute[] attributes= result.getMethod().getAttributes();
		for(CustomAttribute attr:attributes) {
			if (attr.name().toUpperCase().equals("AUTHOR")) {
				String[] authors = attr.values();
				for(String author:authors)
					test.assignAuthor(author);
			}
		}
		
		String[] groups = result.getMethod().getGroups();
		for(String group:groups)
			test.assignCategory(group);

		test.assignDevice(System.getProperty("os.name"));
	}
	
	
	@Override
	public void onFinish(ITestContext context) {
		report.flush();
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		Markup m = MarkupHelper.createLabel("PASS", ExtentColor.GREEN);
		test.pass(m);
		test.log(Status.PASS, "Test passed succesfully");
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		Markup m = MarkupHelper.createLabel("SKIP", ExtentColor.AMBER);
		test.skip(m);
		test.log(Status.SKIP, "Test Skipped");
	}
	
}