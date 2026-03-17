# Bai 1 - BasePage va BaseTest

## Run test

```bash
mvn clean test
```

## Quy tac da ap dung

- Test class chi dung `getDriver()` tu `BaseTest`.
- `driver.findElement()` chi nam trong Page Object (`LoginPage`, `InventoryPage`).
- URL doc tu file config (`config-<env>.properties`).
- Username/password/expectedResult doc tu JSON (`src/test/resources/testdata/login_data.json`).
- Khong su dung `Thread.sleep()`.
- Screenshot duoc chup trong `@AfterMethod` vao `target/screenshots/`.
