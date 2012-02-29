package com.epam.projectname.web.elements;

import org.openqa.selenium.By;

public class SelectElement extends AbstractElement {

	public SelectElement(String locator) {
		super(locator);
	}

	public void select(String optionLocator) {
		try {
//			By value
			driver.findElement(By.xpath(elementLocator+"/option[@value='" + optionLocator + "']")).click();
		} catch (Exception e) {
//			By label
			driver.findElement(By.xpath(elementLocator+"/option[.='" + optionLocator + "']")).click();
		}
	}
	
//	public void multipleSelect(String[] optionLocators) {
//		for (int i = 0; i < optionLocators.length; i++) {
//			selenium.select(elementLocator, optionLocators[i]);
//		}
//	}

	public void check() {
		if(!isChecked())
		{
			driver.findElement(By.xpath(elementLocator)).click();
		}
	}

	public void uncheck() {
		if(isChecked())
		{
			driver.findElement(By.xpath(elementLocator)).click();
		}
	}

	public boolean isChecked() {
		return driver.findElement(By.xpath(elementLocator)).isSelected();
	}

}
