package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.utils.JsonDataReader;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData", parallel = true)
    public Object[][] loginData() {
        return JsonDataReader.readLoginData("testdata/login_data.json");
    }

    @Test(dataProvider = "loginData")
    public void loginByExternalData(String username, String password, String expectedResult) {
        ConfigReader configReader = new ConfigReader(getEnv());
        getDriver().get(configReader.get("base.url"));

        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoaded(), "Login page is not loaded.");

        if ("success".equalsIgnoreCase(expectedResult)) {
            InventoryPage inventoryPage = loginPage.login(username, password);
            Assert.assertTrue(inventoryPage.isLoaded(), "Expected to login successfully.");
        } else {
            loginPage.login(username, password);
            Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface"), "Expected login error message.");
        }
    }
}
