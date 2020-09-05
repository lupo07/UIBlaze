package app.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class CartPage {
	WebDriver driver;
	SeleniumActions selact;
	ExplicitWaits expw;
	SupportValidations spv;
	ConfigReader config = new ConfigReader();;
	private static final Logger log = LogManager.getLogger(CartPage.class.getName());

	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		expw = new ExplicitWaits(driver);
		selact = new SeleniumActions(driver);
		spv= new SupportValidations(driver);
	}

	@FindBy(xpath = "//tr//td[2]")
	WebElement productNameBox;

	@FindBy(xpath = "//tr//td[3]")
	WebElement productPriceBox;

	@FindBy(xpath = "//button[@class='btn btn-success']")
	WebElement placeOrderButton;

	@FindBy(id = "name")
	WebElement nameInput;

	@FindBy(id = "country")
	WebElement countryInput;

	@FindBy(id = "city")
	WebElement cityInput;

	@FindBy(id = "card")
	WebElement cardInput;

	@FindBy(id = "month")
	WebElement monthInput;

	@FindBy(id = "year")
	WebElement yearInput;

	@FindBy(xpath = "//button[contains(text(),'Purchase')]")
	WebElement purchaseButton;

	@FindBy(xpath = "//h2[contains(text(),'Thank you for your purchase!')]")
	WebElement thankYouMessage;
	
	@FindBy(xpath = "//p[contains(@class,'lead text-muted')]")
	WebElement dataMessage;

	@FindBy(xpath = "//button[@class='confirm btn btn-lg btn-primary']")
	WebElement okButton;
	

	public void verifyCartPage(String product, String price) {
		Assert.assertTrue(
				expw.waitForURLToContains(config.getCartURL(), Constants.EXPLICIT_WAIT_CART_PAGE));
		log.info("The Cart URL is displayed correctly");
		Assert.assertTrue(
				expw.waitForVisibilityOfElement(productNameBox, Constants.EXPLICIT_WAIT_CART_PAGE));
		Assert.assertTrue(
				expw.waitForVisibilityOfElement(productPriceBox, Constants.EXPLICIT_WAIT_CART_PAGE));
		
		expw.waitForElementToContain(productNameBox, product, Constants.EXPLICIT_WAIT_CART_PAGE);
		String st = productNameBox.getText();
		log.info("The Actual Product Name is : " + st + " The Expected Product Name is: " + product);
		Assert.assertEquals(product, st);
		
		String st1 = productPriceBox.getText();
		log.info("The Actual Product Price is : " + st1 + " The Expected Product Price is: " + price);
		Assert.assertEquals(price, st1);

	}
	
	public void clickonPlaceOrder() {
		Assert.assertTrue(
				expw.waitForElementToBeClickable(placeOrderButton, Constants.EXPLICIT_WAIT_CART_PAGE));
		selact.clickOnElement(placeOrderButton);
		log.info("The Click on Place Order");
	}
	
	public void clickonPurchase() {
		Assert.assertTrue(
				expw.waitForElementToBeClickable(purchaseButton, Constants.EXPLICIT_WAIT_CART_PAGE));
		selact.clickOnElement(purchaseButton);
		log.info("The Click on Purchase Button");
	}
	
	public void fillTheOrder(List<String> userData) {
		Assert.assertTrue(
				expw.waitForVisibilityOfElement(nameInput, Constants.EXPLICIT_WAIT_CART_PAGE));
		Assert.assertTrue(
				expw.waitForVisibilityOfElement(countryInput, Constants.EXPLICIT_WAIT_CART_PAGE));
		Assert.assertTrue(
				expw.waitForVisibilityOfElement(cityInput, Constants.EXPLICIT_WAIT_CART_PAGE));
		Assert.assertTrue(
				expw.waitForVisibilityOfElement(cardInput, Constants.EXPLICIT_WAIT_CART_PAGE));
		Assert.assertTrue(
				expw.waitForVisibilityOfElement(monthInput, Constants.EXPLICIT_WAIT_CART_PAGE));
		Assert.assertTrue(
				expw.waitForVisibilityOfElement(yearInput, Constants.EXPLICIT_WAIT_CART_PAGE));
		selact.sendTextToElement(nameInput, userData.get(0));
		selact.sendTextToElement(countryInput, userData.get(1));
		selact.sendTextToElement(cityInput, userData.get(2));
		selact.sendTextToElement(cardInput, userData.get(3));
		selact.sendTextToElement(monthInput, userData.get(4));
		selact.sendTextToElement(yearInput, userData.get(5));		
	}
	
	public void verifyPurchase(List<String> userData, String price) {
		Assert.assertTrue(
				expw.waitForVisibilityOfElement(nameInput, Constants.EXPLICIT_WAIT_CART_PAGE));
		String st = selact.getElementText(dataMessage);	
		spv.verifyStringContains(st, price);
		spv.verifyStringContains(st, userData.get(3));
		selact.clickOnElement(okButton);
	}
}
