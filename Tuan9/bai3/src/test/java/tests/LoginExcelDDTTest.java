package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.model.LoginCase;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.utils.ExcelReader;

public class LoginExcelDDTTest extends BaseTest {

    private static final String EXCEL_FILE = "testdata/login_data.xlsx";

    @DataProvider(name = "smokeCases")
    public Object[][] smokeCases() {
        List<LoginCase> cases = ExcelReader.readSheet(EXCEL_FILE, "SmokeCases");
        return toData(cases);
    }

    @DataProvider(name = "regressionCases")
    public Object[][] regressionCases() {
        List<LoginCase> cases = ExcelReader.readAllSheets(EXCEL_FILE, "SmokeCases", "NegativeCases", "BoundaryCases");
        return toData(cases);
    }

    @Test(dataProvider = "smokeCases", groups = {"smoke", "regression"})
    public void testLoginFromSmokeSheet(LoginCase loginCase) {
        executeLoginCase(loginCase);
    }

    @Test(dataProvider = "regressionCases", groups = {"regression"})
    public void testLoginFromAllSheets(LoginCase loginCase) {
        executeLoginCase(loginCase);
    }

    private void executeLoginCase(LoginCase loginCase) {
        setReportTestName(loginCase.getSheetName() + " - " + loginCase.getDescription());

        ConfigReader config = new ConfigReader(getEnv());
        getDriver().get(config.get("base.url"));

        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoaded(), "Login page is not loaded.");

        if (!loginCase.getExpectedUrl().isBlank()) {
            InventoryPage inventoryPage = loginPage.login(loginCase.getUsername(), loginCase.getPassword());
            Assert.assertTrue(inventoryPage.isLoaded(), "Expected successful login for: " + loginCase.getDescription());
            Assert.assertTrue(getDriver().getCurrentUrl().contains(loginCase.getExpectedUrl()));
        } else {
            loginPage.loginExpectingFailure(loginCase.getUsername(), loginCase.getPassword());
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Error must be displayed for: " + loginCase.getDescription());
            Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains(loginCase.getExpectedError().toLowerCase()));
        }
    }

    private Object[][] toData(List<LoginCase> cases) {
        Object[][] data = new Object[cases.size()][1];
        for (int i = 0; i < cases.size(); i++) {
            data[i][0] = cases.get(i);
        }
        return data;
    }
}
