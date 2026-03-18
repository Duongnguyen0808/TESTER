package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import framework.base.BasePage;

public class CheckoutPage extends BasePage {

    private final By firstNameLocator = By.id("first-name");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isElementVisible(firstNameLocator);
    }
}
