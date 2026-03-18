package tests;

import java.util.List;
import java.util.Map;

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
        List<Map<String, String>> rows = JsonDataReader.readRows("testdata/login_data.json");
        return JsonDataReader.toMatrix(rows, "username", "password", "expectedResult");
    }

    @Test(dataProvider = "loginData")
    public void loginWithJsonData(String username, String password, String expectedResult) {
        ConfigReader config = new ConfigReader(getEnv());
        getDriver().get(config.get("base.url"));

        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoaded(), "LoginPage must be loaded first.");

        if ("success".equalsIgnoreCase(expectedResult)) {
            InventoryPage inventoryPage = loginPage.login(username, password);
            Assert.assertTrue(inventoryPage.isLoaded(), "Successful login should open InventoryPage.");
        } else {
            loginPage.loginExpectingFailure(username, password);
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Error banner should be displayed for invalid login.");
        }
    }

    @Test
    public void loginExpectingFailureShouldReturnErrorMessage() {
        ConfigReader config = new ConfigReader(getEnv());
        getDriver().get(config.get("base.url"));

        List<Map<String, String>> rows = JsonDataReader.readRows("testdata/login_data.json");
        Map<String, String> invalid = rows.stream()
            .filter(r -> "fail".equalsIgnoreCase(r.getOrDefault("expectedResult", "")))
            .findFirst()
            .orElseThrow();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(invalid.get("username"), invalid.get("password"));

        Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface"), "Expected Saucedemo error text.");
    }

    @Test
    public void loginSuccessShouldNavigateToInventory() {
        ConfigReader config = new ConfigReader(getEnv());
        getDriver().get(config.get("base.url"));

        List<Map<String, String>> rows = JsonDataReader.readRows("testdata/login_data.json");
        Map<String, String> valid = rows.stream()
            .filter(r -> "success".equalsIgnoreCase(r.getOrDefault("expectedResult", "")))
            .findFirst()
            .orElseThrow();

        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(valid.get("username"), valid.get("password"));
        Assert.assertTrue(inventoryPage.isLoaded(), "InventoryPage should load for valid account.");
    }
}
