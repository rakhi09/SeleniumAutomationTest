package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertTrue;

public class MarketingSegmentList extends Base {

    //Menu
    By markMenuLocator = By.id("mark-tog");
    By markSegmentMenuLocator  = By.xpath("//a[contains(@href, '/segmentation')]");
    By markSegmentListMenuLocator  = By.xpath("//a[contains(@href, '/list')]");

    //List
    By createListLocator = By.id("listxistx_link_create");
    By listNameLocator = By.id("name");
    By listSaveLocator = By.id("save_information");
    By editListLocator = By.xpath("//a[contains(text(),'Edit')]");
    By duplicateListErrorLocator = By.id("error_for_name");
    By listCancelLocator = By.xpath("//a[contains(text(),'Cancel')]");

    //ListFilter
    By listFilterLocator = By.id("listx_table_filter");

    //Messages
    String duplicateListErrorMsg = "Please input a unique value for this field";
    String xPathFormat = "//a[contains(text(),'%s')]";

    public MarketingSegmentList(WebDriver driver) {
        super(driver);
    }

    public void navigateToMarketingList() {
        hoverMenu(markMenuLocator,markSegmentMenuLocator,markSegmentListMenuLocator);
    }

    public Boolean successfullNavigation() {
        return isDisplayed(createListLocator);
    }

    public void addList(String name)
    {
        //Navigating to Marketing > Segmentation > List
        navigateToMarketingList();
        assertTrue("Cannot navigate to Marketing > Segmentation > List", successfullNavigation());

        click(createListLocator);

        switchToPopupWindow();
        assertTrue("Failed to find name of list", isDisplayed(listNameLocator));

        type(name,listNameLocator);
        assertTrue("Failed to find button to save list", isDisplayed(listSaveLocator));

        click(listSaveLocator);
    }

    public Boolean listAddSuccessfull(){
        return isDisplayed(editListLocator);
    }

    public Boolean listAddUnsuccessfull(){
        String error_msg=find(duplicateListErrorLocator).getText();

        //Click cancel
        if(isDisplayed(listCancelLocator)) {
            click(listCancelLocator);
        }

        return error_msg.equalsIgnoreCase(duplicateListErrorMsg);
    }

    public void renameList(String currentName, String newName){

        //Navigating to Marketing > Segmentation > List
        navigateToMarketingList();
        assertTrue("Cannot navigate to Marketing > Segmentation > List", successfullNavigation());

        //Search for list
        type(currentName, listFilterLocator);

        //Select List
        By listToSelectLocator = By.xpath(String.format(xPathFormat,currentName));
        click(listToSelectLocator);
        assertTrue("Unable to find edit button", isDisplayed(editListLocator));

        //Click on edit list
        click(editListLocator);

        //Navigate to popup
        switchToPopupWindow();
        assertTrue("Failed to find name of list", isDisplayed(listNameLocator));

        //Edit the name
        type(newName,listNameLocator);
        assertTrue("Failed to find button to save list", isDisplayed(listSaveLocator));

        //Click on save
        click(listSaveLocator);
    }
}
