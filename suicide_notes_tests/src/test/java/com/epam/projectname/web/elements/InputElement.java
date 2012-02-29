package com.epam.projectname.web.elements;

import org.openqa.selenium.By;

/**
 * Entity that represents input text and text area
 * 
 * @author Sergey_Kaliberda
 * 
 */
public class InputElement extends AbstractElement {

	public InputElement(String locator) {
		super(locator);
		// TODO Auto-generated constructor stub
	}

	public boolean isEditable() {
		return driver.findElement(By.xpath(elementLocator)).isEnabled();
	}

	public void typeText(String text) {
		driver.findElement(By.xpath(elementLocator)).clear();
		driver.findElement(By.xpath(elementLocator)).sendKeys(text);
	}
	
	public String getValue(){
		return driver.findElement(By.xpath(elementLocator)).getAttribute("value");
//		return driver.findElement(By.xpath(elementLocator)).getCssValue("background-color");
		
	}
}