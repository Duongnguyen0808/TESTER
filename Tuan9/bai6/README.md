# Bai 6 - RetryAnalyzer + Flaky Test Simulation

## Da trien khai

- `RetryAnalyzer` implements `IRetryAnalyzer`, doc `maxRetry` tu `ConfigReader` (`retry.count`).
- `RetryListener` implements `IAnnotationTransformer`, ap dung retry analyzer tu dong cho tat ca test.
- Dang ky listener trong `testng.xml` bang the `<listeners>`.
- Co test mo phong flaky: fail 2 lan dau, pass lan thu 3.

## Cac file chinh

- `src/main/java/framework/utils/RetryAnalyzer.java`
- `src/main/java/framework/utils/RetryListener.java`
- `src/test/java/tests/FlakySimulationTest.java`
- `src/test/resources/config-dev.properties` (`retry.count=2`)
- `src/test/resources/config-noretry.properties` (`retry.count=0`)

## Lenh demo

- Retry hoat dong (fail 2 lan, pass lan 3):

```bash
mvn test -Denv=dev
```

- Khong retry (fail ngay lan dau):

```bash
mvn test -Denv=noretry
```
