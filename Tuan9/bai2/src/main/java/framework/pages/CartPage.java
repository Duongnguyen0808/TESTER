package framework.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.base.BasePage;

public class CartPage extends BasePage {

    @FindBy(css = "div.cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = "button.cart_button")
    private List<WebElement> removeButtons;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(css = "div.inventory_item_name")
    private List<WebElement> cartItemNames;

    private final By checkoutButtonLocator = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getItemCount() {
        return cartItems == null ? 0 : cartItems.size();
    }

    public CartPage removeFirstItem() {
        if (!removeButtons.isEmpty()) {
            scrollToElement(removeButtons.get(0));
            waitAndClick(removeButtons.get(0));
        }
        return this;
    }

    public CheckoutPage goToCheckout() {
        waitAndClick(checkoutButton);
        return new CheckoutPage(driver);
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement itemName : cartItemNames) {
            names.add(getText(itemName));
        }
        return names;
    }

    public boolean isLoaded() {
        return isElementVisible(checkoutButtonLocator)
            && "checkout".equals(getAttribute(checkoutButton, "id"));
    }
}
