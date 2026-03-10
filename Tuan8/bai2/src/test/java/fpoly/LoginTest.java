package fpoly;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTest {

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
        System.out.println("[LoginTest] Thread: " + Thread.currentThread().getId() + " - setUp");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("[LoginTest] Thread: " + Thread.currentThread().getId() + " - tearDown");
        DriverFactory.quitDriver();
    }

    // Nhóm smoke: chạy trước mỗi lần deploy (nhanh, kiểm tra chính)
    @Test(groups = {"smoke", "regression"})
    public void testLoginSuccess() {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("standard_user");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/inventory.html"),
                "Dang nhap thanh cong phai chuyen sang /inventory.html, URL hien tai: " + currentUrl);
    }

    // Nhóm regression: kiểm thử đầy đủ (chạy hàng đêm)
    @Test(groups = {"regression"})
    public void testLoginWrongPassword() {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("standard_user");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrong_password");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        String errorText = errorMessage.getText();
        Assert.assertEquals(errorText,
                "Epic sadface: Username and password do not match any user in this service",
                "Thong bao loi khong dung khi nhap sai mat khau");
    }

    // Nhóm sanity: chạy sau khi fix bug cụ thể
    @Test(groups = {"sanity", "regression"})
    public void testLoginLockedUser() {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("locked_out_user");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        String errorText = errorMessage.getText();
        Assert.assertEquals(errorText,
                "Epic sadface: Sorry, this user has been locked out.",
                "Thong bao loi phai la 'Sorry, this user has been locked out' khi dung locked_out_user");
    }
}
