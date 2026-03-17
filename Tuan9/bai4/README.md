# Bai 4 - Data Driven Testing voi JSON + Java Faker

## Phan A - JsonReader

- File `users.json` chua 5 user voi cac truong: username, password, role, expectSuccess, description.
- `UserData` dung Jackson `@JsonProperty` mapping JSON sang POJO.
- `JsonReader.readUsers()` doc danh sach user.
- `UserLoginTest` su dung `@DataProvider` de chay 5 test case tu JSON, gom ca SUCCESS va FAILURE.

## Phan B - Java Faker

- `TestDataFactory` tap trung logic sinh random firstName, lastName, postalCode.
- `CheckoutFakerTest` su dung du lieu random cho form checkout.
- Test in log random data (`System.out.println`) de ban chay 2 lan va doi chieu su khac nhau.

## Ghi chu

- Khong dung `Thread.sleep()`.
- Test class khong goi `driver.findElement()` truc tiep.
- Tat ca test su dung `getDriver()` tu `BaseTest`.
