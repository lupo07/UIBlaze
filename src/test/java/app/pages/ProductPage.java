package app.pages;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import app.common.ConfigReader;
import app.common.Constants;
import app.common.ExplicitWaits;
import app.common.SeleniumActions;
import app.common.SupportValidations;

public class ProductPage {
	WebDriver driver;
	SeleniumActions selact;
	ExplicitWaits expw;
	SupportValidations spv;
	ConfigReader config = new ConfigReader();;
	private static final Logger log = LogManager.getLogger(ProductPage.class.getName());

	public ProductPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		expw = new ExplicitWaits(driver);
		selact = new SeleniumActions(driver);
		spv= new SupportValidations(driver);
	}

	

	@FindBy(xpath = "//h2[@class='name']")
	WebElement productHeader;

	@FindBy(xpath = "//a[@class='btn btn-success btn-lg']")
	WebElement addToCartButton;
	
	@FindBy(xpath = "//a[contains(text(),'Cart')]")
	WebElement cartTab;


	public void verifyProductPage(String product) {
		spv.verifyPageHeader(productHeader, product, Constants.EXPLICIT_WAIT_PRODUCT_PAGE);
	}
	
	public void acceptPopUp() {
		try {
			Thread.sleep(3000);
			Alert alert = driver.switchTo().alert();
			alert.accept();	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.info("The alert was displayed successfully");
	}
	
	public void clickOnAddToCart() {
		Assert.assertTrue(
				expw.waitForElementToBeClickable(addToCartButton, Constants.EXPLICIT_WAIT_PRODUCT_PAGE));
		selact.clickOnElement(addToCartButton);
		log.info("Click on Add to Cart");
	}
	
	public void clickOnCartTab() {
		Assert.assertTrue(
				expw.waitForElementToBeClickable(cartTab, Constants.EXPLICIT_WAIT_PRODUCT_PAGE));
		selact.clickOnElement(cartTab);
		log.info("Click on Cart Tab");
	}

}
