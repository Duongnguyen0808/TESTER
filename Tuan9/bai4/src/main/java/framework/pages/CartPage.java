package framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.base.BasePage;

public class CartPage extends BasePage {

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage goToCheckout() {
        waitAndClick(checkoutButton);
        return new CheckoutPage(driver);
    }
}
