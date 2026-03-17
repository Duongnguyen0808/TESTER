package tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.pages.CartPage;
import framework.pages.CheckoutPage;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.utils.JsonDataReader;

public class CartTest extends BaseTest {

    @DataProvider(name = "cartItems", parallel = true)
    public Object[][] cartItems() {
        List<Map<String, String>> rows = JsonDataReader.readRows("testdata/cart_data.json");
        return JsonDataReader.toMatrix(rows, "itemName");
    }

    @Test
    public void fluentChainShouldWork() {
        ConfigReader config = new ConfigReader(getEnv());
        Map<String, String> valid = getValidUser();

        getDriver().get(config.get("base.url"));

        CartPage cartPage = new LoginPage(getDriver())
            .login(valid.get("username"), valid.get("password"))
            .addFirstItemToCart()
            .goToCart();

        Assert.assertTrue(cartPage.isLoaded(), "Cart page should load through fluent chain.");
        Assert.assertTrue(cartPage.getItemCount() >= 1, "At least one cart item is expected.");
    }

    @Test(dataProvider = "cartItems")
    public void addItemByNameShouldAppearInCart(String itemName) {
        ConfigReader config = new ConfigReader(getEnv());
        Map<String, String> valid = getValidUser();

        getDriver().get(config.get("base.url"));

        InventoryPage inventoryPage = new LoginPage(getDriver())
            .login(valid.get("username"), valid.get("password"));

        CartPage cartPage = inventoryPage
            .addItemByName(itemName)
            .goToCart();

        Assert.assertTrue(cartPage.getItemNames().stream().anyMatch(name -> name.equalsIgnoreCase(itemName)));
    }

    @Test
    public void emptyCartShouldReturnZero() {
        ConfigReader config = new ConfigReader(getEnv());
        Map<String, String> valid = getValidUser();

        getDriver().get(config.get("base.url"));

        CartPage cartPage = new LoginPage(getDriver())
            .login(valid.get("username"), valid.get("password"))
            .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 0, "Cart without add action should be empty.");
    }

    @Test
    public void removeFirstItemShouldNotThrowAndCanCheckout() {
        ConfigReader config = new ConfigReader(getEnv());
        Map<String, String> valid = getValidUser();

        getDriver().get(config.get("base.url"));

        CartPage cartPage = new LoginPage(getDriver())
            .login(valid.get("username"), valid.get("password"))
            .addFirstItemToCart()
            .goToCart()
            .removeFirstItem();

        CheckoutPage checkoutPage = cartPage.goToCheckout();
        Assert.assertTrue(checkoutPage.isLoaded(), "Checkout page should open after clicking checkout.");
    }

    private Map<String, String> getValidUser() {
        return JsonDataReader.readRows("testdata/login_data.json")
            .stream()
            .filter(r -> "success".equalsIgnoreCase(r.getOrDefault("expectedResult", "")))
            .findFirst()
            .orElseThrow();
    }
}
