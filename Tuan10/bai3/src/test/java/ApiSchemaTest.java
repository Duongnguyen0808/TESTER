import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiSchemaTest extends ApiBaseTest {

    // 1. Tạo file schemas/user-list-schema.json kiểm tra response GET /api/users
    @Test(description = "Validation schema: Kiểm tra cấu trúc danh sách User trang 1")
    public void testUserListSchema() {
        given()
            .spec(requestSpec)
        .when()
            .get("/api/users?page=1")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/user-list-schema.json"));
    }

    // 2. Tạo file schemas/user-schema.json kiểm tra response GET /api/users/2
    @Test(description = "Validation schema: Kiểm tra cấu trúc chi tiết 1 User (ID=2)")
    public void testUserSchema() {
        given()
            .spec(requestSpec)
        .when()
            .get("/api/users/2")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }

    // 3. Tạo file schemas/create-user-schema.json kiểm tra response POST /api/users
    @Test(description = "Validation schema: Kiểm tra cấu trúc Response khi tạo User mới")
    public void testCreateUserSchema() {
        java.util.Map<String, String> body = new java.util.HashMap<>();
        body.put("name", "morpheus");
        body.put("job", "leader");

        given()
            .spec(requestSpec)
            .body(body)
        .when()
            .post("/api/users")
        .then()
            .spec(responseSpec)
            .statusCode(201)
            .body(matchesJsonSchemaInClasspath("schemas/create-user-schema.json"));
    }

    // 4. Demo: Thêm field không có trong schema vào expected body -> test phải FAIL
    @Test(description = "Validation schema: Demo lỗi khi thêm trường không khai báo trong schema")
    public void demoExtraFieldFailSchema() {
        /*
         * Giải thích Demo:
         * Trong file schemas/create-user-schema.json mình đã đặt "additionalProperties": false.
         * Tức là Schema chỉ chấp nhận đúng các field: name, job, id, createdAt.
         * 
         * Lợi dụng tính chất của mock server reqres.in là "gửi lên gì nhận về đó" (echo parameters),
         * khi ta nhét thêm trường "tuoi_cua_khi": "100" thì server sẽ trả về JSON có chứa "tuoi_cua_khi" : "100".
         * Khi RestAssured dội vào JsonSchemaValidator (từ file JSON), validator sẽ thấy 1 trường lạ (tuoi_cua_khi) 
         * không có mặt trong danh sách "properties" mà "additionalProperties" lại = false.
         * KIẾT QỦA: Test Case này sẽ bị đỏ lòm (FAIL) y như thầy/cô yêu cầu demo!
         */
        
        java.util.Map<String, String> bodyRequestCoTruongLa = new java.util.HashMap<>();
        bodyRequestCoTruongLa.put("name", "morpheus");
        bodyRequestCoTruongLa.put("job", "leader");
        bodyRequestCoTruongLa.put("tuoi_cua_khi", "100");

        given()
            .spec(requestSpec)
            .body(bodyRequestCoTruongLa)
        .when()
            .post("/api/users")
        .then()
            .spec(responseSpec)
            .statusCode(201) // Status Code Của Request Vẫn Ok vì Mock Chấp nhận
            // Nhưng khi gọi Validate Json Schema với classpath thì báo đỏ vì thừa dữ liệu trả về 
            .body(matchesJsonSchemaInClasspath("schemas/create-user-schema.json"));
    }
}
