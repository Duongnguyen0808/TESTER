package framework.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.base.BasePage;

public class InventoryPage extends BasePage {

    @FindBy(css = "button.btn_inventory")
    private List<WebElement> addButtons;

    @FindBy(css = "a.shopping_cart_link")
    private WebElement cartLink;

    private final By inventoryTitle = By.cssSelector("span.title");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForPageLoad();
        return isElementVisible(inventoryTitle);
    }

    public InventoryPage addFirstItemToCart() {
        if (!addButtons.isEmpty()) {
            waitAndClick(addButtons.get(0));
        }
        return this;
    }

    public CartPage goToCart() {
        waitAndClick(cartLink);
        return new CartPage(driver);
    }
}
