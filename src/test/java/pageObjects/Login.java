package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertTrue;
import config.Config;

public class Login extends Base {

    By loginFormLocator = By.id("email_address");
    By usernameLocator  = By.id("email_address");
    By passwordLocator  = By.id("password");
    By submitButton     = By.xpath("//input[@name='commit']");
    By successMessageLocator = By.cssSelector("img[alt=\"Salesforce Pardot\"]");
    By userIconLocator = By.cssSelector("i.icon.icon-user");
    By logOutLocator = By.xpath("//a[contains(text(),'Sign Out')]");

    public Login(WebDriver driver) {
        super(driver);
        maximize();
        visit(Config.SITE_URL);
        assertTrue("The login form is not present", isDisplayed(loginFormLocator));
    }

    public void with(String username, String password) {
        type(username, usernameLocator);
        type(password, passwordLocator);
        click(submitButton);
    }

    public void logout(){
        hoverMenu(userIconLocator, logOutLocator);
    }

    public Boolean logoutSuccess()
    {
        return isDisplayed(usernameLocator);
    }
    public Boolean successMessagePresent() {
        return isDisplayed(successMessageLocator);
    }
}