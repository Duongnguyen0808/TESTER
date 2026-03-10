package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://www.saucedemo.com/";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginSuccess() {
        // Nhập user/pass hợp lệ
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("standard_user");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Kiểm tra chuyển sang /inventory.html
        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("/inventory.html"),
                "Đăng nhập thành công phải chuyển sang trang /inventory.html, nhưng URL hiện tại là: " + currentUrl);
    }

    @Test
    public void testLoginWrongPassword() {
        // Nhập sai mật khẩu
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("standard_user");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrong_password");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Kiểm tra thông báo lỗi xuất hiện
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        Assert.assertTrue(errorMessage.isDisplayed(),
                "Thông báo lỗi phải xuất hiện khi nhập sai mật khẩu");

        String errorText = errorMessage.getText();
        Assert.assertEquals(errorText,
                "Epic sadface: Username and password do not match any user in this service",
                "Thông báo lỗi không đúng khi nhập sai mật khẩu");
    }

    @Test
    public void testLoginEmptyUsername() {
        // Bỏ trống username
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Kiểm tra thông báo 'Username is required'
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        String errorText = errorMessage.getText();
        Assert.assertEquals(errorText,
                "Epic sadface: Username is required",
                "Thông báo lỗi phải là 'Username is required' khi bỏ trống username");
    }

    @Test
    public void testLoginEmptyPassword() {
        // Bỏ trống password
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("standard_user");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Kiểm tra thông báo 'Password is required'
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        String errorText = errorMessage.getText();
        Assert.assertEquals(errorText,
                "Epic sadface: Password is required",
                "Thông báo lỗi phải là 'Password is required' khi bỏ trống password");
    }

    @Test
    public void testLoginLockedUser() {
        // Dùng 'locked_out_user'
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys("locked_out_user");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Kiểm tra thông báo 'Sorry, this user has been locked out'
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));

        String errorText = errorMessage.getText();
        Assert.assertEquals(errorText,
                "Epic sadface: Sorry, this user has been locked out.",
                "Thông báo lỗi phải là 'Sorry, this user has been locked out' khi dùng locked_out_user");
    }
}
