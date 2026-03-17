package framework.base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest implements ITest {

    private final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    private final ThreadLocal<String> threadLocalEnv = new ThreadLocal<>();
    private final ThreadLocal<String> threadLocalTestName = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return Objects.requireNonNull(threadLocalDriver.get(), "Driver is not initialized in current thread.");
    }

    protected String getEnv() {
        return Objects.requireNonNullElse(threadLocalEnv.get(), "dev");
    }

    protected void setReportTestName(String testName) {
        threadLocalTestName.set(testName);
    }

    @Override
    public String getTestName() {
        return threadLocalTestName.get();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser", "env" })
    public void setUp(@Optional("chrome") String browser, @Optional("dev") String env) {
        threadLocalEnv.set(env);
        threadLocalDriver.set(createDriver(browser));
        getDriver().manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            captureScreenshot(driver, result.getMethod().getMethodName());
            driver.quit();
        }
        threadLocalDriver.remove();
        threadLocalEnv.remove();
        threadLocalTestName.remove();
    }

    private WebDriver createDriver(String browser) {
        String browserName = browser == null ? "chrome" : browser.trim().toLowerCase();
        switch (browserName) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }

    private void captureScreenshot(WebDriver driver, String methodName) {
        try {
            Path screenshotDir = Path.of("target", "screenshots");
            Files.createDirectories(screenshotDir);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
            Path source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            Path target = screenshotDir.resolve(methodName + "_" + timestamp + ".png");
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot save screenshot", ex);
        }
    }
}
