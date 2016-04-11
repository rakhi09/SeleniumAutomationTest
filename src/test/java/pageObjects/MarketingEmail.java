package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertTrue;
import models.Email;

public class MarketingEmail extends Base {

    //Menu
    By markMenuLocator = By.id("mark-tog");
    By markEmailsMenuLocator  = By.xpath("//a[contains(text(),'Emails')]");
    By markEmailsNewEmailListMenuLocator  = By.xpath("//a[contains(text(),'New Email')]");

    //New email
    By emailNameLocator = By.id("name");
    By emailTextOnlyLocator = By.id("email_type_text_only");
    By emailPopupSaveLocator = By.id("save_information");

    //campaign
    By emailPopupChooseCampaign = By.xpath("(//button[@type='button'])[2]");
    By emailCampaignPopupSelectLocator = By.xpath("//div[@class='ember-list-container']/div/div/div/h4");
    By emailCampaignPopupSaveLocator = By.id("select-asset");

    //Template
    By emailTemlateChooseLocator = By.xpath("//div[@id='template_select_list']/div[2]/ul/li");
    By emailTemplateSaveLocator = By.id("template_confirm");

    //Sending
    By emailFlowSendingLocator = By.id("flow_sending");
    By emailFlowSendingSelectListLocator = By.xpath("//div[@id='email-wizard-list-select']/div/div/a/span");
    By emailFlowSendingEnterListTextLocator = By.xpath("//div[@class='chzn-search']/input");
    By emailFlowSendingSelectSenderDropDownLocator = By.name("a_sender[]");
    By emailFlowSendingSenderNameLocator = By.name("a_general_name");
    By emailFlowSendingSenderEmailLocator = By.name("a_general_email");
    By emailFlowSendingSubjectLocator = By.id("subject_a");
    By emailFlowSenderSave = By.id("save_footer");

    public MarketingEmail(WebDriver driver) {
        super(driver);
    }

    public void sendNewEmail(Email email){
        navigateNewEMailMenu();

        switchToPopupWindow();
        assertTrue("Failed to find name of list", isDisplayed(emailNameLocator));

        type(email.name, emailNameLocator);
        click(emailTextOnlyLocator);

        //Choose Campaign btn
        click(emailPopupChooseCampaign);
        assertTrue("Failed to open campaign popup",isDisplayed(emailCampaignPopupSaveLocator));

        //Select campaign & Save Campaign
        click(emailCampaignPopupSelectLocator);
        click(emailCampaignPopupSaveLocator);
        assertTrue("Failed to save campaign",isDisplayed(emailPopupSaveLocator));

        //Save email
        click(emailPopupSaveLocator);
        assertTrue("Failed to save email",isDisplayed(emailTemplateSaveLocator));

        //Select template
        click(emailTemlateChooseLocator);
        click(emailTemplateSaveLocator);

        ForceWait();
        assertTrue("Failed to save template",isDisplayed(emailFlowSendingLocator));

        //Goto Sending
        click(emailFlowSendingLocator);
        assertTrue("Failed to land on Sending",isDisplayed(emailFlowSendingSelectListLocator));

        //Choose lists
        click(emailFlowSendingSelectListLocator);
        find(emailFlowSendingEnterListTextLocator).sendKeys(email.listName, Keys.TAB);

        //choose sender type
        selectDropdownValue(email.senderType, emailFlowSendingSelectSenderDropDownLocator);

        type(email.senderName, emailFlowSendingSenderNameLocator);
        type(email.senderEmail, emailFlowSendingSenderEmailLocator);
        type(email.subject, emailFlowSendingSubjectLocator);

        //Save
        click(emailFlowSenderSave);
    }

    public void navigateNewEMailMenu() {
        hoverMenu(markMenuLocator, markEmailsMenuLocator,markEmailsNewEmailListMenuLocator);
    }
}
