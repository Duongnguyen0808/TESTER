package fpoly.cart;

import fpoly.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CartTest {

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
        // Đăng nhập trước
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        System.out.println("[CartTest] Thread: " + Thread.currentThread().getId() + " - setUp");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("[CartTest] Thread: " + Thread.currentThread().getId() + " - tearDown");
        DriverFactory.quitDriver();
    }

    // Nhóm smoke: kiểm tra thêm sản phẩm vào giỏ
    @Test(groups = {"smoke", "regression"})
    public void testAddToCart() {
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")));
        addButton.click();

        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("shopping_cart_badge")));
        Assert.assertEquals(cartBadge.getText(), "1",
                "So luong san pham trong gio hang phai la 1 sau khi them");
    }

    // Nhóm regression: kiểm tra xóa sản phẩm khỏi giỏ
    @Test(groups = {"regression"})
    public void testRemoveFromCart() {
        // Thêm sản phẩm
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")));
        addButton.click();

        // Xóa sản phẩm
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='remove-sauce-labs-backpack']")));
        removeButton.click();

        // Kiểm tra badge giỏ hàng biến mất
        boolean badgeGone = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.className("shopping_cart_badge")));
        Assert.assertTrue(badgeGone,
                "Badge gio hang phai bien mat sau khi xoa san pham");
    }
}
