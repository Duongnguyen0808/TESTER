package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.base.BasePage;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    private final By loginButtonLocator = By.id("login-button");
    private final By errorMessageLocator = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage login(String user, String pass) {
        waitAndType(usernameField, user);
        waitAndType(passwordField, pass);
        scrollToElement(loginButton);
        waitAndClick(loginButton);
        return new InventoryPage(driver);
    }

    public LoginPage loginExpectingFailure(String user, String pass) {
        waitAndType(usernameField, user);
        waitAndType(passwordField, pass);
        scrollToElement(loginButton);
        waitAndClick(loginButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isElementVisible(errorMessageLocator);
    }

    public boolean isLoaded() {
        return isElementVisible(loginButtonLocator)
            && "login-button".equals(getAttribute(loginButton, "id"));
    }
}
