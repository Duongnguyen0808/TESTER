package framework.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Click an element after it is clickable.
     */
    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * Type text into an element after it is visible.
     */
    protected void waitAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get visible text from an element and trim surrounding spaces.
     */
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText().trim();
    }

    /**
     * Return true if element is visible, false if not found/timeout/stale.
     */
    protected boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException ex) {
            return false;
        }
    }

    /**
     * Scroll element into center of viewport to avoid click interception.
     */
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    /**
     * Wait until document.readyState is complete.
     */
    protected void waitForPageLoad() {
        wait.until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
    }

    /**
     * Get an attribute value after element is visible.
     */
    protected String getAttribute(WebElement element, String attributeName) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getAttribute(attributeName);
    }
}
