package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

/**
 * Bài 6.2: White-Box + Selenium — Kiểm thử form TextBox (demoqa.com)
 * CFG: Nhập liệu → Submit → Validate email → Hiển thị hoặc Error
 * Page Object Model: TextBoxPage
 */
public class TextBoxWhiteBoxTest {

    private WebDriver driver;
    private TextBoxPage page;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://demoqa.com/text-box");
        // Wait for the form to be ready
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.presenceOfElementLocated(By.id("userName")));
        page = new TextBoxPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ==================== CFG PATH TESTS ====================

    @Test(description = "CFG Path 1: Nhập đầy đủ + email hợp lệ → output hiển thị")
    public void testValidFullData() {
        page.fillAndSubmit("Nguyen Van A", "test@example.com", "123 Le Loi", "456 Hai Ba Trung");
        Assert.assertTrue(page.isOutputDisplayed(), "Output section phải hiển thị");
        Assert.assertTrue(page.getOutputName().contains("Nguyen Van A"), "Name phải xuất hiện trong output");
        Assert.assertTrue(page.getOutputEmail().contains("test@example.com"), "Email phải xuất hiện trong output");
    }

    @Test(description = "CFG Path 2: Email sai định dạng → field-error, không hiển thị output")
    public void testInvalidEmail_NoAt() {
        page.fillAndSubmit("Nguyen Van B", "invalidemail", "Dia chi 1", "Dia chi 2");
        Assert.assertTrue(page.isEmailFieldError(), "Email field phải có class field-error");
        Assert.assertFalse(page.isOutputDisplayed(), "Output không hiển thị khi email sai");
    }

    @Test(description = "CFG Path 2b: Email sai — có @ nhưng thiếu domain → error")
    public void testInvalidEmail_IncompleteDomain() {
        page.fillAndSubmit("Test User", "abc@", "Address 1", "Address 2");
        Assert.assertTrue(page.isEmailFieldError(), "Email field phải có class field-error");
        Assert.assertFalse(page.isOutputDisplayed(), "Output không hiển thị");
    }

    @Test(description = "CFG Path 3: Email trống (bỏ qua) → output vẫn hiển thị")
    public void testEmptyEmail_ValidSubmit() {
        page.fillAndSubmit("Tran Thi C", "", "Quan 1 HCM", "");
        Assert.assertTrue(page.isOutputDisplayed(), "Output hiển thị khi email trống");
        Assert.assertTrue(page.getOutputName().contains("Tran Thi C"), "Name hiển thị trong output");
    }

    // ==================== BOUNDARY VALUE TESTS ====================

    @Test(description = "Boundary: Tất cả trường trống → submit → không có output")
    public void testAllEmpty() {
        page.fillAndSubmit("", "", "", "");
        Assert.assertFalse(page.isOutputDisplayed(), "Không có output khi tất cả trống");
    }

    @Test(description = "Boundary: Ký tự đặc biệt trong Name → output hiển thị")
    public void testSpecialCharsInName() {
        page.fillAndSubmit("Nguyễn Văn @#$%", "valid@mail.com", "Địa chỉ <test>", "");
        Assert.assertTrue(page.isOutputDisplayed(), "Output hiển thị với ký tự đặc biệt");
    }

    @Test(description = "Boundary: Email chỉ có @ → error")
    public void testEmailOnlyAt() {
        page.fillAndSubmit("User X", "@", "Address", "");
        Assert.assertTrue(page.isEmailFieldError(), "Email '@' phải bị lỗi");
    }

    @Test(description = "Boundary: Name rất dài → vẫn hiển thị output")
    public void testLongName() {
        String longName = "Nguyen Van ABCDEFGHIJKLMNOPQRSTUVWXYZ ".repeat(5).trim();
        page.fillAndSubmit(longName, "long@test.com", "Address", "");
        Assert.assertTrue(page.isOutputDisplayed(), "Output hiển thị với name dài");
    }

    @Test(description = "Boundary: Email có space → error")
    public void testEmailWithSpace() {
        page.fillAndSubmit("User Y", "test @mail.com", "Address", "");
        Assert.assertTrue(page.isEmailFieldError(), "Email có space phải bị lỗi");
    }

    @Test(description = "Boundary: Chỉ nhập email hợp lệ → output hiển thị email")
    public void testOnlyValidEmail() {
        page.fillAndSubmit("", "only@email.com", "", "");
        Assert.assertTrue(page.isOutputDisplayed(), "Output hiển thị khi chỉ có email");
        Assert.assertTrue(page.getOutputEmail().contains("only@email.com"), "Email hiển thị trong output");
    }
}
