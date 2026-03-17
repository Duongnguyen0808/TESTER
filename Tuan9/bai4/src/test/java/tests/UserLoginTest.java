package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.model.UserData;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.utils.JsonReader;

public class UserLoginTest extends BaseTest {

    @DataProvider(name = "usersData", parallel = true)
    public Object[][] usersData() {
        List<UserData> users = JsonReader.readUsers("testdata/users.json");
        Object[][] data = new Object[users.size()][1];
        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i);
        }
        return data;
    }

    @Test(dataProvider = "usersData")
    public void loginByJsonData(UserData user) {
        ConfigReader config = new ConfigReader(getEnv());
        getDriver().get(config.get("base.url"));

        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoaded(), "Login page should be visible first.");

        if (user.isExpectSuccess()) {
            InventoryPage inventoryPage = loginPage.login(user.getUsername(), user.getPassword());
            Assert.assertTrue(inventoryPage.isLoaded(), "Expected success for: " + user.getDescription());
        } else {
            loginPage.loginExpectingFailure(user.getUsername(), user.getPassword());
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected failure for: " + user.getDescription());
        }
    }
}
