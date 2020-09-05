package app.common;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;




public class SeleniumActions {
	WebDriver driver;
	Actions actions;
	private static final Logger log = LogManager.getLogger(SeleniumActions.class.getName());

	public SeleniumActions(WebDriver driver) {
		this.driver = driver;
		actions = new Actions(driver);
	}

	/**
	 * This Method returns the trimmed text from a WebElement
	 * 
	 * @param webElement
	 * @return string
	 */
	public String getElementText(WebElement webElement) {
		String text = webElement.getText().trim();
		log.info("The text of the WebElement: "+ webElement +" is : "+text);
		return text;
	}
	
	/**
	 * 
	 * This Method clicks on a WebElement
	 * 
	 * @param webElement
	 */
	public void clickOnElement(WebElement webElement) {
		webElement.click();
		log.info("The Click on a WebElement: "+ webElement );
	}

	
	/**
	 * 
	 * This Method Send a String to a WebElement
	 * 
	 * @param webElement
	 * @param text
	 */
	public void sendTextToElement(WebElement webElement, String text) {
		webElement.clear();
		webElement.sendKeys(text);
		log.info("Send the text to the WebElement: "+ webElement  );		
	}
	
	
}
