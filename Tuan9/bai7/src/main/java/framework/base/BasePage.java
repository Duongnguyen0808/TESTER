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

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Click element only when Selenium confirms it is clickable.
     */
    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * Type text after waiting visibility, then clear old value before input.
     */
    protected void waitAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Read visible text and trim spaces around the result.
     */
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText().trim();
    }

    /**
     * Return element visible status and absorb transient stale/timeout exceptions.
     */
    protected boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException ex) {
            return false;
        }
    }

    /**
     * Scroll element to center viewport before interaction.
     */
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    /**
     * Wait for document.readyState to become complete.
     */
    protected void waitForPageLoad() {
        wait.until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
    }

    /**
     * Read an attribute value after visibility wait.
     */
    protected String getAttribute(WebElement element, String attributeName) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getAttribute(attributeName);
    }
}
