package tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.model.UserData;
import framework.pages.CheckoutPage;
import framework.pages.LoginPage;
import framework.utils.JsonReader;
import framework.utils.TestDataFactory;

public class CheckoutFakerTest extends BaseTest {

    @Test
    public void checkoutWithRandomDataFromFactory() {
        ConfigReader config = new ConfigReader(getEnv());
        UserData validUser = JsonReader.readUsers("testdata/users.json")
            .stream()
            .filter(UserData::isExpectSuccess)
            .findFirst()
            .orElseThrow();

        getDriver().get(config.get("base.url"));

        Map<String, String> checkoutData = TestDataFactory.randomCheckoutData();
        System.out.println("Random checkout data: " + checkoutData);

        CheckoutPage checkoutPage = new LoginPage(getDriver())
            .login(validUser.getUsername(), validUser.getPassword())
            .addFirstItemToCart()
            .goToCart()
            .goToCheckout()
            .fillCheckoutForm(
                checkoutData.get("firstName"),
                checkoutData.get("lastName"),
                checkoutData.get("postalCode")
            );

        Assert.assertTrue(checkoutPage.isOverviewLoaded(), "Checkout overview should be loaded after continue.");
    }
}
