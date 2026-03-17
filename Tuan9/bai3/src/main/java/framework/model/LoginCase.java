package framework.model;

public class LoginCase {

    private final String sheetName;
    private final String username;
    private final String password;
    private final String expectedUrl;
    private final String expectedError;
    private final String description;

    public LoginCase(String sheetName, String username, String password, String expectedUrl, String expectedError,
                     String description) {
        this.sheetName = sheetName;
        this.username = username;
        this.password = password;
        this.expectedUrl = expectedUrl;
        this.expectedError = expectedError;
        this.description = description;
    }

    public String getSheetName() {
        return sheetName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getExpectedUrl() {
        return expectedUrl;
    }

    public String getExpectedError() {
        return expectedError;
    }

    public String getDescription() {
        return description;
    }
}
