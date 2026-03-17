# Bai 2 - Page Objects cho Saucedemo

## Chay test

Su dung Maven local hoac Maven global:

```bash
mvn clean test
```

## Dam bao 4 luu y

- Test class chi dung `getDriver()` tu `BaseTest`.
- Khong su dung `driver.findElement()` trong test class.
- Du lieu test URL/user/password doc tu file config va JSON.
- Khong su dung `Thread.sleep()`.

## Page Objects da co

- `LoginPage`: login, loginExpectingFailure, getErrorMessage, isErrorDisplayed
- `InventoryPage`: isLoaded, addFirstItemToCart, addItemByName, getCartItemCount, goToCart
- `CartPage`: getItemCount, removeFirstItem, goToCheckout, getItemNames
- `CheckoutPage`: page object bo sung de ket thuc flow checkout
