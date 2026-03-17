package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import framework.base.BasePage;

public class InventoryPage extends BasePage {

    private final By inventoryTitle = By.cssSelector("span.title");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForPageLoad();
        return isElementVisible(inventoryTitle);
    }
}
