package app.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * This class reads the values from the file configuration.properties
 * 
 * @return Value from configuration.properties
 */
public class ConfigReader {
	private String env;
	private static final Logger log = LogManager.getLogger(ConfigReader.class.getName());
	Properties pro;

	public ConfigReader() {

		try {
			File src = new File("./configuration.properties");
			FileInputStream fis = new FileInputStream(src);

			pro = new Properties();

			pro.load(fis);

		} catch (Exception e) {
			log.debug("Exception is ==" + e.getMessage());
		}
	}


	public String getBaseURL() {
		return pro.getProperty("BaseURL");
	}

	public String getFilterURL() {
		return pro.getProperty("FilterURL");
	}
	
	public String getCartURL() {
		return pro.getProperty("CartURL");
	}

	public String getProduct() {
		return pro.getProperty("Product");
	}
	
	public String getProductPrice() {
		return pro.getProperty("ProductPrice");
	}

	
	public List<String> getUserInformation() {       
        String property = 
        		pro.getProperty("UserInformation");    
        List<String> propertyList = 
        		new ArrayList<>(Arrays.asList(property.split(",")));  
        return propertyList;
    }

}