import io.restassured.response.ValidatableResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiAuthTest extends ApiBaseTest {

    // ==========================================
    // PHẦN B: Data-Driven cho Error Handling (Gộp 3 ý đầu của Phần A)
    // ==========================================

    @DataProvider(name = "loginScenarios")
    public Object[][] loginScenarios() {
        return new Object[][] {
            // email, password, expectedStatus, expectedError
            {"eve.holt@reqres.in", "cityslicka", 200, null},
            {"eve.holt@reqres.in", "", 400, "Missing password"},
            {"", "cityslicka", 400, "Missing email or username"},
            {"notexist@reqres.in", "wrongpass", 400, "user not found"},
            {"invalid-email", "pass123", 400, "user not found"}
        };
    }

    @Test(dataProvider = "loginScenarios", priority = 1, description = "Data-Driven: Kiểm thử login với các kịch bản đúng và sai")
    public void testLoginScenarios(String email, String password, int expectedStatus, String expectedError) {
        Map<String, String> body = new HashMap<>();
        
        // Chỉ truyền email/password vào payload nếu giá trị không rỗng
        if (!email.isEmpty()) body.put("email", email);
        if (!password.isEmpty()) body.put("password", password);

        ValidatableResponse response = given()
            .spec(requestSpec)
            .body(body)
        .when()
            .post("/api/login")
        .then()
            .spec(responseSpec)
            .statusCode(expectedStatus);

        // Validation bổ sung:
        if (expectedError != null) {
            // Check thông báo lỗi nếu có expectedError
            response.body("error", containsString(expectedError));
        } else {
            // Nếu success (expectedError = null), nghĩa là login thành công -> Check token không rỗng
            response.body("token", not(emptyOrNullString()));
        }
    }


    // ==========================================
    // PHẦN A: Bổ sung 2 test script của REGISTER
    // ==========================================

    @Test(priority = 2, description = "Đăng ký thành công tài khoản hợp lệ")
    public void testRegisterSuccess() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");
        body.put("password", "pistol");

        given()
            .spec(requestSpec)
            .body(body)
        .when()
            .post("/api/register")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body("id", notNullValue())
            .body("token", not(emptyOrNullString()));
    }

    @Test(priority = 3, description = "Đăng ký thất bại do thiếu mật khẩu")
    public void testRegisterMissingPassword() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");
        // Không gửi password lên

        given()
            .spec(requestSpec)
            .body(body)
        .when()
            .post("/api/register")
        .then()
            .spec(responseSpec)
            .statusCode(400)
            .body("error", containsString("Missing password"));
    }
}
