package com.epam.projectname.core;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.epam.projectname.core.logging.LoggingWebDriverEventListener;
import com.epam.projectname.core.logging.XHTMLLayout;
import com.epam.projectname.utils.Constants;

public class BaseDriverTest {
	
	private static final String HTML = ".html";
	private Date date = new Date();
	private Format formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	public static Logger log;
	private File seleniumDir;
	private String seleniumDirPath;
	private String imageDirPath;
	private File imageDir;
    protected EventFiringWebDriver driver;
    private static final String MAXIMIZE_BROWSER_WINDOW = "if (window.screen) {window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};";
    
    final StringBuffer resultHtmlFileName = new StringBuffer("report").append(File.separator).append(getCurrentlyExecutingClassName()).append("_").append(formatter.format(date)).append(HTML);
    
//  protected EbselenCommands ebselen;

    /**
     * Start the WebDriver Instance
     */
    

    @BeforeClass(alwaysRun = true)
    protected void setUp()throws Exception{
    	seleniumDir = new File("report");
		seleniumDir.mkdirs();
		imageDir = new File(seleniumDir + File.separator + "IMG");
		imageDir.mkdirs();
//		seleniumDirPath = seleniumDir.getAbsolutePath();
//		imageDirPath = imageDir.getAbsolutePath();
		
		log = Logger.getLogger(BaseDriverTest.class);
		if (log.getLevel() == null)
			log.setLevel(Level.DEBUG);
		
		FileAppender fileappender = new FileAppender(new XHTMLLayout(),resultHtmlFileName.toString());
		log.addAppender(fileappender);

    	driver = new EventFiringWebDriver(new FirefoxDriver());
    	driver.register(new LoggingWebDriverEventListener(log));
		
		maximizeBrowserWindow(driver);
		
		driver.get("http://suicidenote.jelastic.servint.net");
		
		/*
		 * selenium.highlight(locator); selenium.assignId(locator, identifier);
		 * selenium.getXpathCount(xpath) selenium.getAllLinks();
		 */

		// logging
		System.out.println("==============================");
		System.out.println(getCurrentlyExecutingClassName());
		System.out.println("==============================");
		
    }
    
	private static Object maximizeBrowserWindow(WebDriver driver) {
	    return executeJavascript(driver, MAXIMIZE_BROWSER_WINDOW);
	}

	private static Object executeJavascript(WebDriver driver, String script){
	    JavascriptExecutor js=(JavascriptExecutor) driver;
	return js.executeScript(script);
	}
	
	private String getCurrentlyExecutingClassName() {
		return this.getClass().getSimpleName();
	}
	
//    public void startSelenium() {
//        if (driver != null) {
////            logger.error("There appears to be an existing driver instance.. Details are: {}", driver);
////            logger.error("Shutting down existing instance and starting up again...");
//            stopSelenium(driver);
//        }
//        driver = new FirefoxDriver();
////        ebselen = new EbselenCommands(driver);
////        builder = new Actions(driver);
//        
//        
//    }
	
//    /**
//     * Configure the FireFox profile if using FireFox Driver
//     *
//     * @return FirefoxProfile
//     */
//    private FirefoxProfile generateFirefoxProfile() {
//        FirefoxProfile prf = new FirefoxProfile();
//        prf.setPreference("dom.max_chrome_script_run_time", 60);
//        prf.setPreference("setTimeoutInSeconds", 60);
//        prf.setPreference("dom.max_script_run_time", 60);
//        prf.setPreference("dom.popup_maximum", 0);
//        prf.setPreference("privacy.popups.disable_from_plugins", 3);
//        prf.setPreference("browser.xul.error_pages.enabled", false);
//        prf.setPreference("general.useragent.extra.firefox", "Firefox");
//        return (prf);
//    }
    
    /**
     * Shut down any browser instances still open now that tests have finished
     *
     * @param driverObject - driver object to stop
     */
	@AfterClass(alwaysRun = true)
    public void stopDriver() {
		takeScreenshot(true);
        driver.quit();
     }
	
	public boolean takeScreenshot(boolean isNeeded) {
		if (isNeeded) {
			String className = getCurrentlyExecutingClassName();
			String methodName = getCurrentlyExecutingElement().getMethodName();
			StringBuffer generatedFileName = new StringBuffer(className).append("_").append(methodName).append(Calendar.getInstance().getTimeInMillis()).append(Constants.PNG); 
			StringBuffer fileName = new StringBuffer(seleniumDir.toString()).append(File.separator).append("IMG").append(File.separator).append(generatedFileName);
			StringBuffer reportFileName = new StringBuffer("IMG").append(File.separator).append(generatedFileName);
			File scrFile = this.screenshot();
			try {
				FileUtils.copyFile(scrFile, new File(fileName.toString()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.warn(reportFileName);
		}
		return isNeeded;
	}
	
	public File screenshot(){
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	}
	
	private StackTraceElement getCurrentlyExecutingElement() {
		Throwable t = new Throwable();
		StackTraceElement[] elements = t.getStackTrace();
		if (elements.length < 3) return null;
		// if hierarchy will changed please edit this statement!!!
		StackTraceElement calledElement = elements[2];
		return calledElement;
	}
}