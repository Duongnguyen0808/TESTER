# Bai 3 - Data Driven Testing voi Excel

## Da dap ung yeu cau

- Du lieu login doc tu file Excel `login_data.xlsx` gom 3 sheet: SmokeCases, NegativeCases, BoundaryCases.
- `ExcelReader.getCellValue()` xu ly `null` va 4 kieu du lieu: STRING, NUMERIC, BOOLEAN, FORMULA.
- Them dong moi vao Excel la test tu dong chay them, khong can sua code Java.
- Ten test trong report hien theo cot `description` (thong qua `BaseTest implements ITest`).
- testng groups:
  - `smoke`: chi chay data tu sheet SmokeCases
  - `regression`: chay data tu ca 3 sheet
- Khong dung `Thread.sleep()`.

## Chay test

```bash
mvn clean test
```
