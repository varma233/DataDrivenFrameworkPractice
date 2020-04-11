package com.naveen.test.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.naveen.test.base.TestBase;

public class OpenAccountsTest extends TestBase {
	@Test(dataProvider = "dp", dataProviderClass = TestBase.class, groups = "data provider", attributes = @CustomAttribute(name = "Author", values = "Sravani"))
	public void openAccountsTest(String customer, String currency) {
		test.log(Status.INFO, "DATA = {\""+customer+"\" , \""+currency+"\"}");	
		click("btnOpenAccount");
		select("optCustomer", customer);
		select("optCurrency", currency);
		click("btnProcess");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		
	}

}
