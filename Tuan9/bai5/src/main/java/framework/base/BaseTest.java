package framework.base;

import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest {

    private final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return Objects.requireNonNull(threadLocalDriver.get(), "Driver is not initialized in current thread.");
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser", "env" })
    public void setUp(@Optional("chrome") String browser, @Optional("dev") String env) {
        System.setProperty("env", env);

        ConfigReader config = ConfigReader.getInstance();
        System.out.println("Dang dung moi truong: " + config.getEnv());
        System.out.println("explicit wait = " + config.getExplicitWait());

        threadLocalDriver.set(createDriver(browser));
        getDriver().manage().window().maximize();
        getDriver().get(config.getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            driver.quit();
        }
        threadLocalDriver.remove();
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
}
