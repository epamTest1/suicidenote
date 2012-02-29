package com.epam.projectname.testcases.smoke;

import org.testng.annotations.Test;

import com.epam.projectname.core.BaseDriverTest;
import com.epam.projectname.web.structure.pages.SuicideNotePage;

public class TC_SN001 extends BaseDriverTest {

	@Test(groups = { "dev" })
	public void testMakeNote() throws Exception {
		SuicideNotePage snp = new SuicideNotePage(driver); 
		snp.stillWantToDie();
		snp.leaveYourNote();
		snp.sendMyNote();
		assert(snp.isNotesSend());
	}
}