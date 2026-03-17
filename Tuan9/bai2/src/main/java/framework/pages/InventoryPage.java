package framework.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.base.BasePage;

public class InventoryPage extends BasePage {

    @FindBy(css = "div.inventory_list")
    private WebElement inventoryList;

    @FindBy(css = "a.shopping_cart_link")
    private WebElement cartLink;

    @FindBy(css = "span.shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = "div.inventory_item")
    private List<WebElement> products;

    @FindBy(css = "button.btn_inventory")
    private List<WebElement> addToCartButtons;

    private final By inventoryListLocator = By.cssSelector("div.inventory_list");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForPageLoad();
        return isElementVisible(inventoryListLocator)
            && "inventory_list".equals(getAttribute(inventoryList, "class"));
    }

    public InventoryPage addFirstItemToCart() {
        if (!addToCartButtons.isEmpty()) {
            scrollToElement(addToCartButtons.get(0));
            waitAndClick(addToCartButtons.get(0));
        }
        return this;
    }

    public InventoryPage addItemByName(String name) {
        for (WebElement product : products) {
            WebElement itemName = product.findElement(By.cssSelector("div.inventory_item_name"));
            if (name.equalsIgnoreCase(getText(itemName))) {
                WebElement addButton = product.findElement(By.cssSelector("button.btn_inventory"));
                scrollToElement(addButton);
                waitAndClick(addButton);
                break;
            }
        }
        return this;
    }

    public int getCartItemCount() {
        if (!isElementVisible(By.cssSelector("span.shopping_cart_badge"))) {
            return 0;
        }
        return Integer.parseInt(getText(cartBadge));
    }

    public CartPage goToCart() {
        waitAndClick(cartLink);
        return new CartPage(driver);
    }
}
