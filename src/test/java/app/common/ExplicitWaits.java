package app.common;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class has the Explicit waits for elements, based on different inputs
 * such as: - WebElement to be visible - URL to contain - WebElement to be
 * clickable
 * 
 * 
 * @param webEl
 * @param timeout
 * @return boolean
 * 
 */
public class ExplicitWaits {
	WebDriver driver;
	private static final Logger log = LogManager.getLogger(ExplicitWaits.class.getName());

	public ExplicitWaits(WebDriver driver) {
		this.driver = driver;
	}
	
	
	/**
	 * Sets the amount of time in SECONDS to wait for a page load to complete before returns false.
	 * 
	 * If the timeout is negative, page loads can be indefinite.
	 * 
	 * @param timeout
	 * @return Boolean value if the page finished loading in the defined timeout.
	 */
	public boolean waitForPageToLoad(int timeout) {
		try {
			log.info("Waiting for page to load:: " + timeout + " seconds...");
			driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
			return true;

		} catch (Exception e) {
			log.error("The page didn't finished loading... "+ e);
			return false;
		}
	}

	/**
	 * Method to wait for the visibility of a WebElement
	 * 
	 * @param webEl
	 * @param timeout
	 * @return boolean
	 */
	public boolean waitForVisibilityOfElement(WebElement webEl, int timeout) {
		if (webEl != null) {

			try {
				log.info(
						"Waiting for max:: " + timeout + " seconds for element " + webEl.toString() + " to be visible");

				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.visibilityOf(webEl));
				log.info("Element " + webEl.toString() + " is present");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is not present " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	/**
	 * Method to wait for the URL to contain and specific string
	 * 
	 * @param webEl
	 * @param timeout
	 * @return boolean
	 */
	public boolean waitForURLToContains(String expString, int timeout) {
		if (expString != null) {
			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + expString
						+ " to be present in the URL");
				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.urlContains(expString));
				log.info("The URL contains: " + expString);
				return true;

			} catch (Exception e) {
				log.debug("The URL doesn't contains: " + expString + e);
				return false;
			}
		} else
			log.error("The String is null");
		return false;
	}

	/**
	 * Method to wait for the WebElement to contain and specific string
	 * 
	 * @param webEl
	 * @param timeout
	 * @return boolean
	 */
	public boolean waitForElementToContain(WebElement webEl,String expString, int timeout) {
		if (expString != null) {
			try {
				log.info("Waiting for max:: " + timeout + " seconds for string " + expString
						+ " to be present in the Element");
				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.textToBePresentInElement(webEl, expString));
				log.info("The element contains: " + expString);
				return true;

			} catch (Exception e) {
				log.debug("The Element doesn't contains: " + expString + e);
				return false;
			}
		} else
			log.error("The String is null");
		return false;
	}
	
	
	/**
	 * Method to wait for the WebElement to be clickable
	 * 
	 * @param webEl
	 * @param timeout
	 * @return boolean
	 */
	public boolean waitForElementToBeClickable(WebElement webEl, int timeout) {
		if (webEl != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + webEl.toString()
						+ " to be clickable");

				WebDriverWait wait = new WebDriverWait(driver, timeout);

				wait.until(ExpectedConditions.elementToBeClickable(webEl));
				log.info("Element " + webEl.toString() + " is clickable");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is not clickable " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;

	}
	
	/**
	 * Method for wait the visibility of an element using the fluent wait
	 * 
	 * @param webEl
	 * @param timeout
	 * @param polling
	 * @return boolean
	 */
	public boolean fluentWaitForVisibilityOfElement(WebElement webEl, int timeout, int polling) {
		if (webEl != null) {

			try {
				log.info("Waiting for max: " + timeout + " seconds for element " + webEl.toString()
						+ " with polling of " + polling + " to be visible");

				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
						.pollingEvery(polling, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
				
				wait.until(ExpectedConditions.visibilityOf(webEl));
				log.info("Element " + webEl.toString() + " is present");
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " is not present " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;
	}

	

	/**
	 * Looks for a List of WebElements repeatedly by pooling intervals until timeout happens or until the object is found.
	 * 
	 * @param webEl - List/collection of WebWlement
	 * @param timeout - Time in SECONDS
	 * @param polling - Time in SECONDS
	 * 
	 * @return Boolean value - Returns false if the whole List of elements is not visible after the timeout.
	 */
	public boolean fluentWaitForVisibilityOfElements(List<WebElement> webEl, int timeout, int polling) {
		if (webEl != null) {
			List<WebElement> webEl2 = webEl;
			try {

				log.info("Waiting for max: " + timeout + " seconds for element " + webEl2.toString()
				+ " with polling of " + polling + " to be visible");

				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
						.pollingEvery(polling, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
				wait.until(ExpectedConditions.visibilityOfAllElements(webEl2));
				log.info("Elements " + webEl2.toString() + " are present");
				return true;

			} catch (Exception e) {
				log.debug("Elements " + webEl2.toString() + " are not present " + e);
				return false;
			}
		} else
			log.error("Elements are null");
		return false;
	}

	/**
	 * Method to wait for the visibility of a group of Elements
	 * 
	 * @param webEl - List/collection of WebWlement
	 * @param timeout - Time in SECONDS
	 * @return Boolean value - Returns false if either one element of the List is still visible after the timeout or true when all elements are not visible anymore
	 */
	public boolean waitForInvisibilityOfElements(List<WebElement> allContainers, int timeout) {
		if (allContainers != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for elements " + allContainers.toString()
				+ " to be available");

				WebDriverWait wait = new WebDriverWait(driver, timeout);
				wait.until(ExpectedConditions.invisibilityOfAllElements(allContainers));
				log.info("Elements " + allContainers.toString() + " are present");
				return true;

			} catch (Exception e) {
				log.debug("Elements " + allContainers.toString() + " are not present " + e);
				return false;
			}
		} else
			log.error("Elements are null");
		return false;

	}
	
	public boolean javaScriptWaitWholePage(int timeout) {
		try {
			log.info(
					"Waiting for max:: " + timeout + " seconds for the Page to be visible");
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until((ExpectedCondition<Boolean>) wd ->
			   ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));			
			log.info("The Page was loaded");
			return true;
		} catch (Exception e) {
			log.debug("The page didn't load " + e);
			return false;
		}

	}
	

	/**
	 * 
	 *  Method to wait for an Attribute from the element to contain certain value
	 * 
	 * @param webEl
	 * @param attribute
	 * @param value
	 * @param timeout
	 * @return
	 */
	public boolean waitForAttributeToBe(WebElement webEl,String attribute,String value,int timeout) {
		if (webEl != null) {

			try {
				log.info("Waiting for max:: " + timeout + " seconds for element " + webEl.toString()
						+ " for the Attribute: "+ attribute+ " to contain: "+ value);

				WebDriverWait wait = new WebDriverWait(driver, timeout);

				wait.until(ExpectedConditions.attributeContains(webEl, attribute,value));
				log.info("Element " + webEl.toString() + " contains: "+ value);
				return true;

			} catch (Exception e) {
				log.debug("Element " + webEl.toString() + " not contains value " + e);
				return false;
			}
		} else
			log.error("Element is null");
		return false;

	}
	

}
