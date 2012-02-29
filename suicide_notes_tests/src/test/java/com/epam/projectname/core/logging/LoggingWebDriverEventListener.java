package com.epam.projectname.core.logging;
import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class LoggingWebDriverEventListener implements WebDriverEventListener{
	private Logger log;
	private By lastFindBy;
    private String originalValue;
    
    public LoggingWebDriverEventListener(Logger log){
    	this.log = log;
    }
    
    public void afterChangeValueOf(WebElement element, WebDriver selenium){
        log.debug("WebDriver changing value in element found "+lastFindBy+" from \""+originalValue+"\" to \""+element.getAttribute("value")+"\"");
    }
    public void afterClickOn(WebElement element, WebDriver selenium) {
    	log.debug("WebDriver clicking element found " + lastFindBy);
    }
    public void afterFindBy(By by, WebElement element, WebDriver selenium) {}
    public void afterNavigateBack(WebDriver selenium) {
    	log.info("WebDriver navigating back");
    }
    public void afterNavigateForward(WebDriver selenium) {
    	log.info("WebDriver navigating forward");
    }
    public void afterNavigateTo(String url, WebDriver selenium) {}
    public void afterScript(String script, WebDriver selenium) {}
    public void beforeChangeValueOf(WebElement element, WebDriver selenium){
        originalValue = element.getAttribute("value");
    }
    public void beforeClickOn(WebElement element, WebDriver selenium) {}
    public void beforeFindBy(By by, WebElement element, WebDriver selenium){
        lastFindBy = by;
    }
    public void beforeNavigateBack(WebDriver selenium) {}
    public void beforeNavigateForward(WebDriver selenium) {}
    public void beforeNavigateTo(String url, WebDriver selenium){
        log.info("WebDriver navigating to: \""+url+"\"");
    }
    public void beforeScript(String script, WebDriver selenium) {}
    public void onException(Throwable error, WebDriver selenium){
        if (error.getClass().equals(NoSuchElementException.class)){
            log.error("WebDriver error: Element not found "+lastFindBy);
        } else {
            log.error("WebDriver error:", error);
        }
    }
}