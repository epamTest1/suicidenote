package com.epam.projectname.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * @author Sergey_Kaliberda
 */

public class AbstractElement  {

	protected EventFiringWebDriver driver;

	protected String elementLocator;

	public AbstractElement(String locator) {
		this.elementLocator = locator;
	}

	public void setDriver(EventFiringWebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * @param selenium
	 * @uml.property name="selenium"
	 */
//
//	public String getLocator() {
//		return elementLocator;
//	}
//
//	public String toString() {
//		return elementLocator;
//	}
//
	public String getAttribute(String attribute) {
		return driver.findElement(By.xpath(elementLocator)).getAttribute(attribute);
	}

	public void click() throws Exception {
		if (isElementPresent()) {
			driver.findElement(By.xpath(elementLocator)).click();
		}
		
	}
//
//	public void warmClick() {
//		if (selenium.isElementPresent(elementLocator)) {
//			selenium.mouseOver(elementLocator);
//			selenium.mouseDown(elementLocator);
//			selenium.mouseUp(elementLocator);
//		}
//	}
//
//	public void doubleClick() {
//		selenium.doubleClick(elementLocator);
//	}
//
//	public boolean isVisible() {
//		return selenium.isVisible(elementLocator);
//	}
		
	public boolean isElementPresent() throws Exception {
		try {
			driver.findElement(By.xpath(elementLocator)).isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean waitForElement() throws Exception {
		return waitForElement(8000);
	}

	public boolean waitForElement(long time) throws Exception {
		return waitForElement(time, 5);
	}

	public synchronized boolean waitForElement(long time, int timesToTry) throws Exception {

		for (int i = 0; i < timesToTry; i++) {
			try {
				Thread.sleep(time);
			} catch (Exception e) {
				System.out.println("Interrupted exception");
			}
			if (isElementPresent()) {
				return true;
			}
		}
		return false;
	}

//
//	public void focus() {
//		selenium.focus(elementLocator);
//	}

}
