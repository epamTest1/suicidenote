package com.epam.projectname.web.structure.pages;

import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.epam.projectname.core.BaseDriverTest;
import com.epam.projectname.web.elements.InputElement;
import com.epam.projectname.web.elements.NavigationElement;
import com.epam.projectname.web.elements.SelectElement;
import com.epam.projectname.web.structure.BasePage;

public class SuicideNotePage extends BasePage {
    public SuicideNotePage(EventFiringWebDriver driver){
    	super(driver);
    	BaseDriverTest.log.info("Suiside Note Page is opened");
    }

	public void stillWantToDie() throws Exception {
        NavigationElement wantToDie = getNavigationElement("//a[contains(text(),'Still want')]");
        wantToDie.click();
		
	}

	public void leaveYourNote() {
		InputElement fromName = getInputElement("//input[@id='from']");
		SelectElement to = getSelectElement("//select[@id='to']");
		InputElement note = getInputElement("//textarea[@id='i-want-to-say']");
		InputElement sendTo = getInputElement("//input[@id='send-to']");
		InputElement when = getInputElement("//input[@id='when']");
		fromName.typeText("Test FirstName Test LastName");
		to.select("Friends");
		note.typeText("This is my last word");
		sendTo.typeText("notking@mailinator.com");
		when.typeText("02/29/2012 15:41");
	}

	public void sendMyNote() throws Exception {
        NavigationElement sendMyNote = getNavigationElement("//button[@id='send-my-note']");
        sendMyNote.click();

		
	}

	public boolean isNotesSend() {
		// TODO Auto-generated method stub
		return false;
	}

}
