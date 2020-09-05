package app.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import app.common.ConfigReader;
import app.common.Constants;
import app.common.ExplicitWaits;
import app.common.SeleniumActions;
import app.common.SupportValidations;

public class ProductStoreLPage {
	WebDriver driver;
	SeleniumActions selact;
	ExplicitWaits expw;
	SupportValidations spv;
	ConfigReader config = new ConfigReader();
	private static final Logger log = LogManager.getLogger(ProductStoreLPage.class.getName());

	public ProductStoreLPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		expw = new ExplicitWaits(driver);
		selact = new SeleniumActions(driver);
	}

	@FindAll(@FindBy(xpath = "//h4/a"))
	List<WebElement> products;

	@FindAll(@FindBy(xpath = "//div[@class='card-block']//h5"))
	List<WebElement> prodPrice;
	
	@FindBy(xpath = "//a[contains(text(),'Laptops')]")
	WebElement laptopsFilter;

	public void clickLaptopsFilter() {
		Assert.assertTrue(
				expw.waitForElementToBeClickable(laptopsFilter, Constants.EXPLICIT_WAIT_PRODUCT_PAGE));
		selact.clickOnElement(laptopsFilter);
		log.info("The Click on Filter");
	}

	public void clickOnTheProduct(String product) {
		Assert.assertTrue(
				expw.waitForURLToContains(config.getFilterURL(), Constants.EXPLICIT_WAIT_PRODUCT_PAGE));
		log.info("The URL is displayed correctly");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i <= products.size() - 1; i++) {
			String str = selact.getElementText(products.get(i));
			log.info("Product: " + str);
			if (str.contains(product)) {
				selact.clickOnElement(products.get(i));
				log.info("The Click on the element " + product );
				break;
			} else if (!str.contains(str)) {
				log.info("The product doesn't contain the name");
			} else if (!str.contains(str) && i <= products.size() - 1) {
				log.info("The product is not present");
			}
		}
	}

}
