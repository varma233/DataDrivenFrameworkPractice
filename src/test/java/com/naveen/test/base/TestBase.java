package com.naveen.test.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.naveen.test.utilities.ExcelReader;
import com.naveen.test.utilities.ExtentManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties objectRepository = new Properties();
	public static FileInputStream fis = null;

	public static String rootDirectory = System.getProperty("user.dir");
	public static String browser;
	public static WebDriverWait wait;
	public static ExcelReader excel = new ExcelReader(rootDirectory + "\\src\\test\\resources\\excel\\TestData.xlsx");
	public static ExtentReports report= ExtentManager.getInstance();
	public static ExtentTest test;

	static {
		System.setProperty("log4j.configurationFile", "./log4j2.xml");
		System.setProperty("org.uncommons.reportng.escape-output", "false");
	}

	public Logger log = LogManager.getLogger(TestBase.class);

	@BeforeSuite
	public void setUp() throws IOException, Exception {

		log.trace("******************* TEST EXECUTION - STARTED *******************");

		if (driver == null) {
			fis = new FileInputStream(rootDirectory + "\\src\\test\\resources\\properties\\config.properties");
			config.load(fis);
			log.trace("Config.properties file loaded");

			fis = new FileInputStream(rootDirectory + "\\src\\test\\resources\\properties\\OR.properties");
			objectRepository.load(fis);
			log.trace("OR.properties file loaded");

			String browserSystemVariable = System.getenv("browser");
			if(browserSystemVariable != null && !browserSystemVariable.isEmpty())
				config.setProperty("browser", browserSystemVariable);
			
			browser = config.getProperty("browser");
			log.debug("browser name=" + browser);

			switch (browser.toUpperCase()) {
			case "CHROME":
				log.trace("Detected chrome browser");
				System.setProperty("wedriver.chrome.driver",
						rootDirectory + "\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.trace("Chrome Driver inititialized");
				break;

			case "FIREFOX":
				log.trace("Detected FIREFOX browser");
				System.setProperty("wedriver.gecko.driver",
						rootDirectory + "\\src\\test\\resources\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
				log.trace("Firefox Driver initialized");
				break;
			case "IE":
				log.trace("Detected IE browser");
				System.setProperty("wedriver.ie.driver",
						rootDirectory + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				InternetExplorerOptions options = new InternetExplorerOptions();
//				options.disableNativeEvents();
				options.introduceFlakinessByIgnoringSecurityDomains();
//				options.requireWindowFocus();
				driver = new InternetExplorerDriver(options);
				log.trace("IE Driver initialized");
				break;

			default:
				log.error("Browser not supported".toUpperCase());
				throw new Exception("Please check browser name in \"config.properties\" file. "
						+ "Supported browsers are IE & CHROME");
			}
		}

		if (driver != null) {
//			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.timeout")),
					TimeUnit.SECONDS);
			driver.get(config.getProperty("baseUrl"));
			log.debug("Launched baseURL : " + config.getProperty("baseUrl"));

			wait = new WebDriverWait(driver, Integer.parseInt(config.getProperty("explicit.timeout")));
		}

	}

	@AfterSuite
	public  void tearDown() throws InterruptedException {
		if (driver != null)
			Thread.sleep(1000);
		log.trace("Quitting browser");
		driver.get("file:\\\\"+rootDirectory+"\\target\\extentreport\\index.html");
		log.trace("******************* TEST EXECUTION - ENDED *******************\n\n");

	}

	public boolean isElementFound(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			log.error("Exception:" + e.getMessage());
			return false;
		}
	}

	public void click(String selector) {
		if (browser.toUpperCase().equals("IE"))
			driver.findElement(By.cssSelector(objectRepository.getProperty(selector))).sendKeys(Keys.ENTER);
		else
			driver.findElement(By.cssSelector(objectRepository.getProperty(selector))).click();
		
		Reporter.log("<br/>Clicked " + selector);
		test.log(Status.INFO, "Clicked \"" + selector);
	}

	public void type(String selector, String keys) {
		driver.findElement(By.cssSelector(objectRepository.getProperty(selector))).clear();
		driver.findElement(By.cssSelector(objectRepository.getProperty(selector))).sendKeys(keys);
		Reporter.log("<br/>Typed text \"" + keys + "\" into " + selector);
		test.log(Status.INFO, "Typed text \"" + keys + "\" into " + selector);
	}
	
	
	public void select(String selector, String text) {
		WebElement we = driver.findElement(By.cssSelector(objectRepository.getProperty(selector)));
		Select select = new Select(we);
		select.selectByVisibleText(text);
		Reporter.log("<br/>Selected option \"" + text+"\"");
		test.log(Status.INFO, "Selected option \""+text+"\"");
	}
	
	@DataProvider(name="dp")
	public Object[][] getCustomerData(Method m){
		Object [][] data;
		String sheetName = m.getName();
		System.out.println("sheetname="+sheetName);
		int rowCount = excel.getRowCount(sheetName);
		int colCount = excel.getColumnCount(sheetName);
		data = new Object[rowCount-1][colCount];
		
		if(rowCount > 0 ) {
			for(int row=2 ; row <= rowCount; row++) {			
				for(int col=0; col < colCount; col++) {
					data[row-2][col] = excel.getCellData(sheetName, col, row);
				}
			}
		}	
		
		return data;
	}

}
