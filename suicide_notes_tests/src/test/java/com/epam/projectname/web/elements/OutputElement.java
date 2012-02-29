package com.epam.projectname.web.elements;

import org.openqa.selenium.By;


/**
 * Entity that represents output text and image
 * @author Oleksandr_Kachur
 *
 */
public class OutputElement extends AbstractElement{

	public OutputElement(String locator) {
		super(locator);
	}
	
	public String getTextValue(){
		return driver.findElement(By.xpath(elementLocator)).getText();
	}
	
	public String getPictureUrl() {
		return getAttribute("src");
	}
}