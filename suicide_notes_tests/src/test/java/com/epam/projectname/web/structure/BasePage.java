package com.epam.projectname.web.structure;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;


import com.epam.projectname.web.elements.InputElement;
import com.epam.projectname.web.elements.NavigationElement;
import com.epam.projectname.web.elements.OutputElement;
import com.epam.projectname.web.elements.SelectElement;
import com.epam.projectname.web.elements.AbstractElement;

public abstract class BasePage /*implements Page*/ {
	protected EventFiringWebDriver driver;
	
	public BasePage(EventFiringWebDriver driver) {
		this.driver = driver;
	}
//  TODO: check is it needed here
//	public abstract void open();
	
    public int getElementCount(String locator) {
        List elementsFound = driver.findElements(By.xpath(locator));
        return elementsFound.size();
    }
	
	
	public Boolean isTextPresent(String text) {
		return driver.findElement(By.tagName("body")).getText().contains(text);
	}

	public void goBack() {
		driver.navigate().back();
	}
	

	protected NavigationElement getNavigationElement(String locator) {
		NavigationElement webElement = new NavigationElement(locator);
		webElement.setDriver(driver);
		return webElement;
	}

	protected InputElement getInputElement(String locator) {
		InputElement webElement = new InputElement(locator);
		webElement.setDriver(driver);
		return webElement;
	}

	protected OutputElement getOutputElement(String locator) {
		OutputElement webElement = new OutputElement(locator);
		webElement.setDriver(driver);
		return webElement;
	}

	protected SelectElement getSelectElement(String locator) {
		SelectElement webElement = new SelectElement(locator);
		webElement.setDriver(driver);
		return webElement;
	}

	protected AbstractElement getWebElement(String locator) {
		AbstractElement webElement = new AbstractElement(locator);
		webElement.setDriver(driver);
		return webElement;
	}

	public void refresh() {
		driver.navigate().refresh();
	}

	public String getTitle() {
		return driver.getTitle();
	}

	

//	public void open(String url) {
//		selenium.open(url);
//		waitForPageToLoad();
//
//	}
//	

}
