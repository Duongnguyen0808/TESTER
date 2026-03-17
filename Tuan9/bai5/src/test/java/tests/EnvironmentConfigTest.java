package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.pages.LoginPage;

public class EnvironmentConfigTest extends BaseTest {

    @Test
    public void shouldReadConfigByEnvWithoutCodeChange() {
        ConfigReader config = ConfigReader.getInstance();

        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoaded(), "Login page should be loaded from configured URL.");

        if ("dev".equals(config.getEnv())) {
            Assert.assertEquals(config.getExplicitWait(), 15, "Dev env explicit wait must be 15.");
        }

        if ("staging".equals(config.getEnv())) {
            Assert.assertEquals(config.getExplicitWait(), 20, "Staging env explicit wait must be 20.");
        }
    }
}
