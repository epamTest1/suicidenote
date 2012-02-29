package com.epam.projectname.web.structure;

//import com.epam.fourseasons.tests.web.elements.BreadCrumb;
//import com.epam.fourseasons.tests.web.elements.WebElement;


public interface Page {
	void goBack();
	void refresh();
	boolean isTextPresent(String pattern);
	boolean isElementPresent(String locator);
//	boolean isElementPresent(WebElement element);
	String getTitle();
	void waitForPageToLoad();
	boolean verifyPage();
	boolean verifyTitle(String expectedTitle);
	void submitForm();	
}
