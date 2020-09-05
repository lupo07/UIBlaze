package app.suites;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import app.common.ConfigReader;
import app.common.DriverSetUp;
import app.common.UsefulMethods;
import app.pages.CartPage;
import app.pages.ProductPage;
import app.pages.ProductStoreLPage;

public class PurchaseProducts {
	WebDriver driver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports report;
	ExtentTest test;
	DriverSetUp ds;
	ProductStoreLPage pdlp;
	ProductPage pp;
	UsefulMethods um;
	CartPage cp;
	ConfigReader config = new ConfigReader();
	private static final Logger log = LogManager.getLogger(PurchaseProducts.class.getName());

	@BeforeTest
	public void beforeTest() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");
		LocalDateTime now = LocalDateTime.now();

		report = new ExtentReports();
		htmlReporter = new ExtentHtmlReporter(
				"./Reports/Report_" + PurchaseProducts.class.getName() + dtf.format(now).toString() + "_Suite.html");
		htmlReporter.loadXMLConfig("extent-config.xml");
		report.attachReporter(htmlReporter);
	}

	@BeforeMethod
	@Parameters({ "browser" })
	public void setClasses(String browser) throws MalformedURLException {
		log.info("The Test Suite " + PurchaseProducts.class.getName() + " has started");

		ds = new DriverSetUp(browser);
		driver = ds.driveReturn();
		log.info("Local Driver");

		log.info(" Base Url: " + config.getBaseURL() + " Browser: " + browser);
		log.info("Driver: " + driver);

		pdlp = new ProductStoreLPage(driver);
		pp = new ProductPage(driver);
		cp = new CartPage(driver);
		um = new UsefulMethods(driver);

		driver.get(config.getBaseURL());
		driver.manage().window().maximize();
	}

	@Test
	public void purchaseLaptop() {
		test = report.createTest("Purchase a laptop");
		log.info("\n ----------------------------------------------------------------------");
		log.info(" -------------- Step 1 Search for the Product ------------------");
		test.log(Status.INFO, "Step 1 Search for the Product");
		pdlp.clickLaptopsFilter();
		pdlp.clickOnTheProduct(config.getProduct());
		
		log.info("\n ----------------------------------------------------------------------");
		log.info(" -------------- Step 2 Add the product to the cart ------------------");
		test.log(Status.INFO, "Step 2 Add the product to the cart");
		pp.verifyProductPage(config.getProduct());
		pp.clickOnAddToCart();
		pp.acceptPopUp();
		
		log.info("\n ----------------------------------------------------------------------");
		log.info(" -------------- Step 3 Go to the Cart------------------");
		test.log(Status.INFO, "Step 3 Go to the Cart");
		pp.clickOnCartTab();
		
		log.info("\n ----------------------------------------------------------------------");
		log.info(" -------------- Step 4 Complete the purchase ------------------");
		test.log(Status.INFO, "Step 4 Complete the purchase");
		cp.verifyCartPage(config.getProduct(),config.getProductPrice());
		cp.clickonPlaceOrder();
		cp.fillTheOrder(config.getUserInformation());
		cp.clickonPurchase();
		cp.verifyPurchase(config.getUserInformation(), config.getProductPrice());
		
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		try {
			if (result.getStatus() == ITestResult.SUCCESS) {
				log.info("======PASSED=====");
				test.log(Status.PASS, "The Test Case " + result.getName() + " is PASS");

			} else if (result.getStatus() == ITestResult.FAILURE) {
				log.info("======FAILED=====");
				String method = result.getName();
				String temp = um.getScreenshotBase64(driver, method);
				StringWriter sw = new StringWriter();
				result.getThrowable().printStackTrace(new PrintWriter(sw));
				String exStackTrace = sw.toString();
				log.info("Exception: " + exStackTrace);
				MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromBase64String(temp)
						.build();
				test.log(Status.FAIL, new RuntimeException(exStackTrace), mediaModel);

			} else if (result.getStatus() == ITestResult.SKIP) {
				log.info("======SKIPPED=====");
				test.log(Status.SKIP, "The Test Case " + result.getName() + " is SKIP");
				StringWriter sw = new StringWriter();
				result.getThrowable().printStackTrace(new PrintWriter(sw));
				String exStackTrace = sw.toString();
				log.info("Exception: " + exStackTrace);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("----------------------------------------------------------------------");
		log.info(" -------------- The Test " + result.getName() + " has been completed");
		log.info("----------------------------------------------------------------------");
		driver.quit();
	}

	@AfterTest
	public void afterTest() {
		report.flush();
	}
}
