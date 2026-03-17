package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.base.BasePage;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorBanner;

    private final By loginButtonLocator = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage login(String username, String password) {
        waitAndType(usernameInput, username);
        waitAndType(passwordInput, password);
        scrollToElement(loginButton);
        waitAndClick(loginButton);
        return new InventoryPage(driver);
    }

    public boolean isLoaded() {
        return isElementVisible(loginButtonLocator)
            && "login-button".equals(getAttribute(loginButton, "id"));
    }

    public String getErrorMessage() {
        return getText(errorBanner);
    }
}
