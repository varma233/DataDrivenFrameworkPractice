package com.naveen.test.testcases;

import org.testng.annotations.CustomAttribute;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.naveen.test.base.TestBase;


public class BankManagerLoginTest extends TestBase{	
//	Logger log = LogManager.getLogger(BankManagerLoginTest.class);

	@Test(groups = "login test", attributes = @CustomAttribute(name = "Author", values = "Naveen"))
	public void bankManagerLoginTest () throws InterruptedException {
		log.trace("Executing LoginAsBranchManager test");				
		click("btnBankManagerLogin");
//		assertTrue(isElementFound(By.cssSelector(objectRepository.getProperty("btnAddCustomer"))));
//		log.debug("Add Customer button found");
	}
}
