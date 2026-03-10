package fpoly;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CheckoutTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Parameters({"baseUrl", "browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("https://www.saucedemo.com/") String baseUrl,
                       @Optional("chrome") String browser) {
        DriverFactory.initDriver(browser);
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(baseUrl);
        // Đăng nhập
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        // Thêm sản phẩm vào giỏ
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        System.out.println("[CheckoutTest] Thread: " + Thread.currentThread().getId() + " - setUp");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("[CheckoutTest] Thread: " + Thread.currentThread().getId() + " - tearDown");
        DriverFactory.quitDriver();
    }

    // Nhóm smoke: kiểm tra vào trang checkout
    @Test(groups = {"smoke", "regression"})
    public void testGoToCheckout() {
        // Vào giỏ hàng
        WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.className("shopping_cart_link")));
        cartLink.click();
        wait.until(ExpectedConditions.urlContains("/cart.html"));

        // Nhấn Checkout
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='checkout']")));
        checkoutBtn.click();

        wait.until(ExpectedConditions.urlContains("/checkout-step-one.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout-step-one.html"),
                "Phai chuyen sang trang checkout step one");
    }

    // Nhóm regression: kiểm tra checkout thiếu thông tin
    @Test(groups = {"regression"})
    public void testCheckoutMissingInfo() {
        // Vào giỏ hàng
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("/cart.html"));

        // Nhấn Checkout
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='checkout']")));
        checkoutBtn.click();
        wait.until(ExpectedConditions.urlContains("/checkout-step-one.html"));

        // Nhấn Continue mà không điền thông tin
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='continue']")));
        continueBtn.click();

        // Kiểm tra thông báo lỗi
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
        String errorText = errorMessage.getText();
        Assert.assertEquals(errorText,
                "Error: First Name is required",
                "Thong bao loi phai la 'First Name is required' khi bo trong thong tin");
    }
}
