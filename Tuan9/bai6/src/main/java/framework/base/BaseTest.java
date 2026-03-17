package framework.base;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import framework.config.ConfigReader;

public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "env" })
    public void beforeEach(@Optional("dev") String env) {
        System.setProperty("env", env);
        ConfigReader config = ConfigReader.getInstance();
        System.out.println("Dang dung moi truong: " + config.getEnv());
        System.out.println("retry.count = " + config.getRetryCount());
    }
}
