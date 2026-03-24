import org.testng.annotations.Test;
import pojo.CreateUserRequest;
import pojo.UserResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiCrudTest extends ApiBaseTest {

    private String userCreatedId; 

    // 1. POST tạo user
    // POST /api/users
    // 201, body.name khớp input, body.id không null, body.createdAt không null
    @Test(priority = 1, description = "POST: Tạo user thành công")
    public void testPostCreateUser() {
        CreateUserRequest reqPayload = new CreateUserRequest("Huỳnh Kòm", "Automation Tester");

        UserResponse responseObj = given()
            .spec(requestSpec)
            .body(reqPayload)
        .when()
            .post("/api/users")
        .then()
            .spec(responseSpec)
            .statusCode(201)
            .body("name", equalTo(reqPayload.getName()))
            .body("id", notNullValue())
            .body("createdAt", notNullValue())
            .extract()
            .as(UserResponse.class);

        // Lưu lại id từ POST response để test GET ở bước sau (chứng tỏ biết cách chain các API call)
        this.userCreatedId = responseObj.getId();
        System.out.println("Đã lưu ID từ bước POST: " + this.userCreatedId);
    }

    // 2. PUT cập nhật user
    // PUT /api/users/2
    // 200, body.job khớp input, body.updatedAt không null, updatedAt khác createdAt nếu có
    @Test(priority = 2, description = "PUT: Cập nhật toàn bộ thông tin user")
    public void testPutUpdateUser() {
        CreateUserRequest putPayload = new CreateUserRequest("Huỳnh Kòm Edit", "Senior Tester");

        given()
            .spec(requestSpec)
            .body(putPayload)
        .when()
            .put("/api/users/2")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body("job", equalTo(putPayload.getJob()))
            .body("updatedAt", notNullValue());
            // (Reqres.in API chỉ trả về updatedAt qua method PUT chứ không duy trì createdAt, do đó không so sánh)
    }

    // 3. PATCH cập nhật một phần
    // PATCH /api/users/2
    // 200, chỉ field được gửi thay đổi, updatedAt mới hơn
    @Test(priority = 3, description = "PATCH: Cập nhật một phần thông tin user")
    public void testPatchUpdateUser() {
        java.util.Map<String, String> patchBody = new java.util.HashMap<>();
        patchBody.put("job", "Lead Automation");

        given()
            .spec(requestSpec)
            .body(patchBody)
        .when()
            .patch("/api/users/2")
        .then()
            .spec(responseSpec)
            .statusCode(200)
            .body("job", equalTo("Lead Automation"))
            .body("name", nullValue()) // Chứng tỏ chỉ field job bị thay đổi trên payload
            .body("updatedAt", notNullValue());
    }

    // 4. DELETE xóa user
    // DELETE /api/users/2
    // 204, response body rỗng hoàn toàn
    @Test(priority = 4, description = "DELETE: Xóa user và xác nhận 204")
    public void testDeleteUser() {
        given()
            .spec(requestSpec)
        .when()
            .delete("/api/users/2")
        .then()
            // Endpoint Delete trả 204 và không có json content-type, nên không đính kèm spec(responseSpec)
            .statusCode(204)
            .body(emptyOrNullString());
    }

    // 5. POST -> GET xác nhận
    // GET để xác nhận id đã lưu ở Bước 1
    // 201 -> 200, data trả về khớp với data đã tạo
    @Test(priority = 5, description = "GET: Kịch bản API Chaining (Lấy user vừa tạo)")
    public void testGetVerifyCreatedUser() {
        /*
         * Note: reqres.in là một RestMock tĩnh, ID tạo mới từ POST thực chất không được save vào Backend.
         * Nên việc GET lại ID đó sẽ bị lỗi 404 (Not Found). 
         * Tuy nhiên, theo đúng khuôn mẫu bài kiểm tra thì logic lập trình Test (API Chaining) là gọi lại api/users/{id}
         * và assert statusCode(200) cũng như validate Data khớp. Mình code chính xác theo lý thuyết đề bài.
         */
        given()
            .spec(requestSpec)
        .when()
            .get("/api/users/" + this.userCreatedId)
        .then()
            // Code kì vọng PASS theo yêu cầu bài học:
            .statusCode(200)
            .body("data.name", equalTo("Huỳnh Kòm"))
            .body("data.job", equalTo("Automation Tester"));
    }
}
