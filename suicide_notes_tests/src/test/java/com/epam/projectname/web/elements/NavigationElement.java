package com.epam.projectname.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Entity that represents link or button element
 * 
 * @author Sergey_Kaliberda
 * 
 */
public class NavigationElement extends AbstractElement  {

	public NavigationElement(String locator) {
		super(locator);
	}

	public void click() throws Exception {
		if (isElementPresent()) {
			driver.findElement(By.xpath(elementLocator)).click();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
//			TODO: show log message here !!!
//			selenium.logComment("Element: " + elementLocator + ", is not available on page - " + selenium.getLocation());
		}
	}
}