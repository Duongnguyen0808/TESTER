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

    private final By loginButtonLocator = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage login(String username, String password) {
        waitAndType(usernameField, username);
        waitAndType(passwordField, password);
        waitAndClick(loginButton);
        return this;
    }

    public boolean isLoaded() {
        return isElementVisible(loginButtonLocator);
    }
}
