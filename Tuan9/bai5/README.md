# Bai 5 - ConfigReader + Da moi truong

## Da dap ung yeu cau

- Tao 2 file config: `config-dev.properties` va `config-staging.properties`.
- `ConfigReader` dung Singleton pattern va doc theo `System.getProperty("env", "dev")`.
- `BaseTest.setUp()` goi `System.setProperty("env", env)` truoc khi khoi tao `ConfigReader`.
- Khong hardcode URL hay timeout o noi nao khac ngoai file config.
- Log moi truong va explicit wait de demo.

## Lenh demo

```bash
mvn test -Denv=dev
```

Log ky vong:

- Dang dung moi truong: dev
- explicit wait = 15

```bash
mvn test -Denv=staging
```

Log ky vong:

- Dang dung moi truong: staging
- explicit wait = 20
