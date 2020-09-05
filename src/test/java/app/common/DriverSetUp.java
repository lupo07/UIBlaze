package app.common;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSetUp {
	WebDriver driver;
	RemoteWebDriver rdriver;
	String browser;
	private static final Logger log = LogManager.getLogger(DriverSetUp.class.getName());

	public DriverSetUp(String browser) {
		this.browser = browser;
	}

	/**
	 * 
	 * Method to select the driver specified by the user
	 *  
	 * @return Driver
	 */
	public WebDriver driveReturn() {
		System.setProperty(
                "webdriver.chrome.driver",
                "./BrowserDrivers/chromedriver.exe");
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver();			
			} else if (browser.equalsIgnoreCase("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				options.addPreference("browser.download.folderList", 1);
				options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
				options.addPreference("intl.accept_languages","en-us");
				options.addPreference("pdfjs.disabled", true);
				driver = new FirefoxDriver(options);
			}
		} catch (Exception e) {
			log.error(browser + " is not a valid browser");
			e.printStackTrace();
		}
		return driver;
	}

	
	/**
	 * 
	 * Method to select the driver specified by the user
	 *  
	 * @return Driver
	 */
	public RemoteWebDriver driveReturn(String url) {
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				DesiredCapabilities cap= DesiredCapabilities.chrome();
				URL u = new URL (url);
				rdriver = new RemoteWebDriver(u, cap);	
			} else if (browser.equalsIgnoreCase("firefox")) {
				DesiredCapabilities cap= DesiredCapabilities.firefox();
				URL u = new URL (url);
				rdriver = new RemoteWebDriver(u, cap);
			}
		} catch (Exception e) {
			log.error(browser + " is not a valid browser");
			e.printStackTrace();
		}
		return rdriver;
	}

}
