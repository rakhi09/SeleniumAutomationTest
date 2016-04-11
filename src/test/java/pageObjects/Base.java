package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import config.Config;

import java.util.List;

public class Base {

    private WebDriver driver;

    public Base(WebDriver driver) {
        this.driver = driver;
    }

    public void maximize() {
        this.driver.manage().window().maximize();
    }

    public void visit(String url) {
        driver.get(url);
    }

    public WebElement find(By locator) {
        return driver.findElement(locator);
    }

    public void click(By locator) {
        if(isDisplayed(locator)) {
            find(locator).click();
        }
    }

    public void clickWithScroll(By locator){
        if(isDisplayed(locator)) {
            JavascriptExecutor je = (JavascriptExecutor) driver;
            WebElement element = find(locator);
            je.executeScript("arguments[0].scrollIntoView(true);",element);
            find(locator).click();
        }
    }

    public void type(String inputText, By locator) {
        if(isDisplayed(locator)) {
            find(locator).clear();
            find(locator).sendKeys(inputText);
        }
    }

    public Boolean verifyText(String text){
        try {
            return driver.getPageSource().contains(text);
        }
        catch (Exception ex){
            return false;
        }
    }

    public void selectDropdownValue(String text, By locator) {
        if(isDisplayed(locator)) {
            Select dropdown = new Select(find(locator));
            dropdown.selectByVisibleText(text);
        }
    }

    public Boolean isDisplayed(By locator) {
        try {
            waitFor(ExpectedConditions.visibilityOfElementLocated(locator), Config.DEFAULT_WAIT_TIME);
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    public void ForceWait()
    {
        try{
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hoverMenu(By menu, By subMenu) {
        Actions action = new Actions(driver);

        //Navigate to main menu
        Hover(action, menu);

        //Click menu to select
        click(subMenu);
    }

    public void hoverMenu(By menu, By subMenu, By innerSubMenu)
    {
        Actions action = new Actions(driver);

        //Navigate to main menu
        Hover(action, menu);

        if(isDisplayed(subMenu))
        {
            //Navigate to sub-menu
            Hover(action, subMenu);

            //Click menu to select
            click(innerSubMenu);
        }
    }

    public Boolean switchToPopupWindow()
    {
        try {
            wait(10);
            driver.switchTo().activeElement();
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }

    private void waitFor(ExpectedCondition<WebElement> condition, Integer timeout) {
        timeout = timeout != null ? timeout : Config.DEFAULT_WAIT_TIME;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(condition);
    }

    private void Hover(Actions action, By locator)
    {
        WebElement element = driver.findElement(locator);
        Actions moveAction =  action.moveToElement(element);
        moveAction.build().perform();
    }
}
