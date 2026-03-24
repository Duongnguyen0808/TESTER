import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiGetTest extends ApiBaseTest {

    // Test 1: GET /api/users?page=1 -> Kiểm tra status 200, page=1, total_pages > 0, data.size() >= 1
    @Test(description = "Kiểm tra GET danh sách user trang 1")
    public void testGetUsersPage1() {
        given()
                .spec(requestSpec)
                .queryParam("page", 1)
        .when()
                .get("/api/users")
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(1))
                .body("total_pages", greaterThan(0))
                .body("data.size()", greaterThanOrEqualTo(1));
    }

    // Test 2: GET /api/users?page=2 -> Kiểm tra page=2, mỗi user trong data[] có id, email, first_name, last_name, avatar
    @Test(description = "Kiểm tra GET danh sách user trang 2 và xác nhận cấu trúc dữ liệu")
    public void testGetUsersPage2() {
        given()
                .spec(requestSpec)
                .queryParam("page", 2)
        .when()
                .get("/api/users")
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", everyItem(hasKey("id")))
                .body("data", everyItem(hasKey("email")))
                .body("data", everyItem(hasKey("first_name")))
                .body("data", everyItem(hasKey("last_name")))
                .body("data", everyItem(hasKey("avatar")));
    }

    // Test 3: GET /api/users/3 -> Kiểm tra id=3, email đúng định dạng @reqres.in, first_name không rỗng
    @Test(description = "Kiểm tra GET chi tiết user id 3 với định dạng email đúng")
    public void testGetSingleUser() {
        given()
                .spec(requestSpec)
        .when()
                .get("/api/users/3")
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data.id", equalTo(3))
                .body("data.email", endsWith("@reqres.in"))
                .body("data.first_name", not(emptyOrNullString()));
    }

    // Test 4: GET /api/users/9999 -> Kiểm tra status 404, body là object rỗng {}
    @Test(description = "Kiểm tra GET user không tồn tại trả về lỗi 404 và body rỗng")
    public void testGetNotFoundUser() {
        given()
                .spec(requestSpec)
        .when()
                .get("/api/users/9999")
        .then()
                .spec(responseSpec)
                .statusCode(404)
                .body("$", equalTo(Collections.emptyMap()));
    }
}
