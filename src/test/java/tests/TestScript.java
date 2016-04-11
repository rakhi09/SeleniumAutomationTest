package tests;

import config.Config;
import models.Prospect;
import models.Email;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import pageObjects.Login;
import pageObjects.MarketingEmail;
import pageObjects.MarketingSegmentList;
import pageObjects.ProspectList;

import java.text.SimpleDateFormat;
import java.util.Date;



public class TestScript {

    private WebDriver driver;
    private Login login;
    private MarketingSegmentList marketingSegmentList;
    private ProspectList prospectList;
    private MarketingEmail marketingEmail;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        login = new Login(driver);
        marketingSegmentList = new MarketingSegmentList(driver);
        prospectList = new ProspectList(driver);
        marketingEmail = new MarketingEmail(driver);
    }

    @Test
    public void PreScreeningInterviewScriptTest() {
        /* 1. Marketing > Segmentation > List - Add */

        //Login to System
        login.with(Config.USER_EMAIL, Config.PASSWORD);
        assertTrue("success message not present", login.successMessagePresent());

        //Setup listName
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddhhmmss");
        String listName = "Test-" + ft.format(date);

        //Adding a new List
        marketingSegmentList.addList(listName);
        assertTrue("Failed to add list", marketingSegmentList.listAddSuccessfull());

        //Adding a new List with same name, should not be successful
        marketingSegmentList.addList(listName);
        assertTrue("Failed to add list", marketingSegmentList.listAddUnsuccessfull());

        //Rename the list
        marketingSegmentList.renameList(listName, listName + "2");
        assertTrue("Failed to rename list", marketingSegmentList.listAddSuccessfull());

        //Adding a new List with same again, should be successfull
        marketingSegmentList.addList(listName);
        assertTrue("Failed to add list", marketingSegmentList.listAddSuccessfull());

        /* 2. Prospects > Prospect lists - Add */

        String prospectName = "P" + ft.format(date);

        //Create prospect object
        Prospect prospect = new Prospect();
        prospect.firstName = prospectName+"FN";
        prospect.lastName = prospectName+"LN";
        prospect.email = prospectName + "@test.com";
        prospect.campaign = "Allison Tigers";
        prospect.profile = "Alan Dawgs 1";
        prospect.score = "100";
        prospect.list = listName;

        //Add prospect
        prospectList.addProspect(prospect);
        assertTrue("Failed to add prospect", prospectList.prospectAddSuccessful());
        assertTrue(prospectList.verifyProspectListAdded(prospect.list));

        /* 3. Marketing > Email > New Email */

        Email email = new Email();
        email.name = "TestEmail"+ft.format(date);
        email.listName = listName;
        email.senderType = "General User";
        email.senderName = "pardottest";
        email.senderEmail = "pardottest@test.com";
        email.subject = "This is a pardot test subject";

        marketingEmail.sendNewEmail(email);

        /* 4. Logout */

        login.logout();
        assertTrue("Failure in logging out", login.logoutSuccess());
    }

    @Test
    public void ListRenameTest(){

        //Login to System
        login.with(Config.USER_EMAIL, Config.PASSWORD);
        assertTrue("success message not present", login.successMessagePresent());

        //Rename of List
        marketingSegmentList.renameList("abc-1231", "abc-1232");
        assertTrue("Failed to rename list", marketingSegmentList.listAddSuccessfull());
    }

    @Test
    public void ProspectTest(){

        //Login to System
        login.with(Config.USER_EMAIL, Config.PASSWORD);
        assertTrue("success message not present", login.successMessagePresent());

        //Setup prospect
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddhhmmss");
        String prospectName = "P" + ft.format(date);

        //Create prospect object
        Prospect prospect = new Prospect();
        prospect.firstName = prospectName+"FN";
        prospect.lastName = prospectName+"LN";
        prospect.email = prospectName + "@test.com";
        prospect.campaign = "Allison Tigers";
        prospect.profile = "Alan Dawgs 1";
        prospect.score = "100";
        prospect.list = "abc-1232";

        //Add prospect
        prospectList.addProspect(prospect);
        assertTrue("Failed to add prospect", prospectList.prospectAddSuccessful());
        assertTrue(prospectList.verifyProspectListAdded(prospect.list));
    }

    @Test
    public void MarketingEmailTest(){
        //Login to System
        login.with(Config.USER_EMAIL, Config.PASSWORD);
        assertTrue("success message not present", login.successMessagePresent());

        Email email = new Email();
        email.name = "test124";
        email.listName = "abc-1232";
        email.senderType = "General User";
        email.senderName = "pardottest";
        email.senderEmail = "pardottest@test.com";
        email.subject = "This is a pardot test subject";

        marketingEmail.sendNewEmail(email);
    }

    @Test
    public void LogOutTest(){
        //Login to System
        login.with(Config.USER_EMAIL, Config.PASSWORD);
        assertTrue("success message not present", login.successMessagePresent());

        //Logout
        login.logout();
        assertTrue("Failure in logging out", login.logoutSuccess());
    }
    @After
    public void tearDown() {
        driver.quit();
    }
}
