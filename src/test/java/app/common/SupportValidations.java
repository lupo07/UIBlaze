package app.common;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class SupportValidations {
	WebDriver driver;
	SeleniumActions selact;
	ExplicitWaits exwait;
	ConfigReader config = new ConfigReader();
	private static final Logger log = LogManager.getLogger(SupportValidations.class.getName());

	public SupportValidations(WebDriver driver) {
		this.driver = driver;
		exwait = new ExplicitWaits(driver);
		selact = new SeleniumActions(driver);
	}

	/**
	 * 
	 * This method verifies that a WebElement has the right Header
	 * 
	 * @param actualStr
	 * @param expectedStr
	 */
	public void verifyStringContains(String actualStr, String expectedStr) {
		log.info("The Actual String is Contains the Expected String: " + expectedStr);
		Assert.assertTrue(actualStr.contains(expectedStr));
	}
	
	
	/**
	 * 
	 * This method verifies that a WebElement has the right Header
	 * 
	 * @param wElement
	 * @param expectedHeader
	 * @param timeout
	 */
	public void verifyPageHeader(WebElement wElement, String expectedHeader, int timeout) {
		Assert.assertTrue(
				exwait.waitForVisibilityOfElement(wElement, timeout));
		String str = selact.getElementText(wElement);

		log.info("The Actual Header is: " + str + " The Expected Header is: " + expectedHeader);
		Assert.assertEquals(str, expectedHeader);
	}
	
	
	

	

}
