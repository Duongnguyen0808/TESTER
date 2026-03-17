package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.base.BasePage;

public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    private final By checkoutOverviewTitle = By.cssSelector("span.title");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage fillCheckoutForm(String firstName, String lastName, String postalCode) {
        waitAndType(firstNameField, firstName);
        waitAndType(lastNameField, lastName);
        waitAndType(postalCodeField, postalCode);
        waitAndClick(continueButton);
        return this;
    }

    public boolean isOverviewLoaded() {
        return isElementVisible(checkoutOverviewTitle);
    }
}
