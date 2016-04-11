package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertTrue;
import models.Prospect;

public class ProspectList extends Base {

    //Menu
    By prospectMenuLocator = By.id("pro-tog");
    By prospectListMenuLocator  = By.xpath("//a[contains(text(),'Prospect List')]");

    //List
    By createListLocator = By.id("pr_link_create");

    //Add prospect page
    By prospectFirstNameLocator = By.id("default_field_3361");
    By prospectLastNameLocator = By.id("default_field_3371");
    By prospectEmailLocator = By.id("email");
    By prospectCampaignLocator = By.id("campaign_id");
    By prospectProfileLocator = By.id("profile_id");
    By prospectScoreLocator = By.id("score");
    By prospectListExpandLocator = By.id("toggle-inputs-lists-");
    By prospectListSelectLocator = By.xpath("//div[@id='pr_fields_lists_wrapper_']/div/div/div/div/a/span");
    By prospectListNameTextFilterLocator = By.cssSelector("div.chzn-search > input[type=\"text\"]");
    By prospectCreateLocator = By.name("commit");

    //Prospect view
    By prospectViewEditLocatar = By.xpath("//a[contains(text(),'Edit')]");
    By prospectViewListLocator = By.xpath("//div[@class='navbar-inner']/ul/li[2]/a");
    By prospectViewListLoadSuccessLocator = By.className("chzn-container-single");

    //Messages
    String prospectSuccessfulMsg = "Prospect saved successfully";

    public ProspectList(WebDriver driver) {
        super(driver);
    }

    public void navigateToProspectList() {
        hoverMenu(prospectMenuLocator,prospectListMenuLocator);
    }

    public void addProspect(Prospect prospect) {

        //Navigating to Prospects > Prospect List
        navigateToProspectList();
        assertTrue("Cannot navigate to Marketing > Segmentation > List", successfulNavigation());

        //Click on add prospect
        click(createListLocator);

        //Fill in values
        type(prospect.firstName, prospectFirstNameLocator);
        type(prospect.lastName, prospectLastNameLocator);
        type(prospect.email, prospectEmailLocator);
        selectDropdownValue(prospect.campaign, prospectCampaignLocator);
        selectDropdownValue(prospect.profile, prospectProfileLocator);
        type(prospect.score, prospectScoreLocator);

        clickWithScroll(prospectListExpandLocator);
        click(prospectListSelectLocator);
        find(prospectListNameTextFilterLocator).sendKeys(prospect.list, Keys.TAB);

        //Create
        click(prospectCreateLocator);
    }

    public Boolean verifyProspectListAdded(String listName){

        //Click on list
        click(prospectViewListLocator);

        //Verify if list tab loaded & verify list
        if(isDisplayed(prospectViewListLoadSuccessLocator)){
            return verifyText(listName);
        }
        return false;
    }

    public Boolean successfulNavigation() {
        return isDisplayed(createListLocator);
    }

    public Boolean prospectAddSuccessful(){

        if(isDisplayed(prospectViewEditLocatar)){
            return verifyText(prospectSuccessfulMsg);
        }
        return false;
    }
}
