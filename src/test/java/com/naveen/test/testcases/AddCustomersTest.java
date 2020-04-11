package com.naveen.test.testcases;

import static org.testng.Assert.assertTrue;
import java.util.Random;
import org.testng.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.naveen.test.base.TestBase;

public class AddCustomersTest extends TestBase {

	@Test(dataProvider = "dp", dataProviderClass = TestBase.class, groups = {
			"data provider" }, attributes = @CustomAttribute(name = "Author", values = "Naveen"))
	public void addCustomersTest(String fname, String lname, String pcl) throws InterruptedException {
		test.log(Status.INFO, "DATA = {\"" + fname + "\" , \"" + lname + "\" , \"" + pcl + "\"}");

		click("btnAddCustomer");
		type("txtFirstName", fname);
		type("txtlastName", lname);
		type("txtPostalCode", pcl);
		click("btnAddCustomerSubmit");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		try {
			assertTrue(alert.getText().contains("Customer added successfully"), "Alert text mismatch");
		} catch (AssertionError e) {
			alert.accept();
			e.printStackTrace();
		}
		alert.accept();

		String failtest = System.getenv("fail-testcases");
		System.out.println("fail-testcases = "+failtest);
		if (failtest.toUpperCase().equals("YES")) {

			Random r = new Random();
			int number = r.nextInt() % 5;
			if (number % 4 == 0) {
//				Assert.fail("Number is : " + number);
			}
		}

	}

}
