import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PerformanceSlaTest extends ApiBaseTest {

    // 1. DataProvider cho bảng kịch bản với 5 data row
    @DataProvider(name = "slaData")
    public Object[][] slaData() {
        return new Object[][] {
            // method, endpoint, SLA tối đa (maxMs)
            {"GET", "/api/users", 2000L},
            {"GET", "/api/users/2", 1500L},
            {"POST", "/api/users", 3000L},
            {"POST", "/api/login", 2000L},
            {"DELETE", "/api/users/2", 1000L}
        };
    }

    // 2. Viết method run chung 5 bộ test được truyền DataProvider
    @Test(dataProvider = "slaData", description = "Performance SLA Monitoring: Kiểm tra Response Time từng Endpoint")
    public void testSlaMonitoring(String method, String endpoint, long maxMs) {
        // Gọi hàm bọc có annotation @Step ở bên dưới
        executeApiCallWithSlaCheck(method, endpoint, maxMs);
    }

    // 3. Thêm Allure @Step annotation cực kỳ tường minh
    @Step("Gọi {method} {endpoint} - SLA: {maxMs}ms")
    public void executeApiCallWithSlaCheck(String method, String endpoint, long maxMs) {
        
        RequestSpecification request = given().spec(requestSpec);
        
        // Mớm payload cho các lệnh POST giả lập
        if (method.equals("POST") && endpoint.equals("/api/users")) {
            java.util.Map<String, String> mapCreate = new java.util.HashMap<>();
            mapCreate.put("name", "morpheus");
            mapCreate.put("job", "leader");
            request.body(mapCreate);
        } else if (method.equals("POST") && endpoint.equals("/api/login")) {
            // body login chuẩn phải để token trả ra 100% thay vì lỗi 400
            java.util.Map<String, String> mapLogin = new java.util.HashMap<>();
            mapLogin.put("email", "eve.holt@reqres.in");
            mapLogin.put("password", "cityslicka");
            request.body(mapLogin);
        }

        // Thực thi request tùy thuộc dạng string param Method bằng Switch-case mạnh mẽ
        Response response = null;
        switch (method.toUpperCase()) {
            case "GET": 
                response = request.when().get(endpoint); 
                break;
            case "POST": 
                response = request.when().post(endpoint); 
                break;
            case "DELETE": 
                response = request.when().delete(endpoint); 
                break;
        }

        // 4. In console log cho thấy thời gian thực tế của mỗi API call như rập khuôn yêu cầu!
        long actualTime = response.getTime();
        System.out.println("=================================================");
        System.out.println(String.format("👉 LOG THỜI GIAN THỰC TẾ :: %s %s lấy ra %d ms (Chuẩn SLA: <= %d ms)", method, endpoint, actualTime, maxMs));
        System.out.println("=================================================");

        ValidatableResponse vResponse = response.then();
        
        // Assert bắt buộc quan trọng nhất: Time SLA
        vResponse.time(lessThanOrEqualTo(maxMs));

        // Bổ trợ riêng lẻ cho từng spec Assertions ở bảng
        if (method.equals("GET") && endpoint.equals("/api/users")) {
            vResponse.statusCode(200).body("data.size()", greaterThanOrEqualTo(1));
        } else if (method.equals("GET") && endpoint.equals("/api/users/2")) {
            vResponse.statusCode(200).body("data.id", equalTo(2));
        } else if (method.equals("POST") && endpoint.equals("/api/users")) {
            vResponse.statusCode(201).body("id", notNullValue());
        } else if (method.equals("POST") && endpoint.equals("/api/login")) {
            vResponse.statusCode(200).body("token", notNullValue());
        } else if (method.equals("DELETE") && endpoint.equals("/api/users/2")) {
            vResponse.statusCode(204); // Mặc định xoá báo 204 No Content
        }
    }

    // 5. Test chạy API 10 lần liên tiếp để tính average/min/max
    @Test(description = "Performance SLA Monitoring: Tính toán thống kê Min/Max/Avg trong 10 lần gọi")
    public void testMonitoringLoadTesting() {
        System.out.println("----------------------------------------------");
        System.out.println("BẮT ĐẦU MÔ PHỎNG MONITORING 10 LẦN CHO GET /api/users");
        
        long totalTime = 0;
        long minTime = Long.MAX_VALUE;
        long maxTime = Long.MIN_VALUE;

        for (int i = 1; i <= 10; i++) {
            long time = given().spec(requestSpec).when().get("/api/users").getTime();
            
            totalTime += time;
            if (time < minTime) minTime = time;
            if (time > maxTime) maxTime = time;
            
            System.out.println(String.format("► Lần %02d: %4d ms", i, time));
        }

        long avgTime = totalTime / 10;
        
        System.out.println("\n========= 📊 KẾT QUẢ MONITORING SLA 📊 =========");
        System.out.println("⏱ Average Response Time (Trung bình) : " + avgTime + " ms");
        System.out.println("⏱ Min Response Time (Thấp nhất)      : " + minTime + " ms");
        System.out.println("⏱ Max Response Time (Chậm nhất)      : " + maxTime + " ms");
        System.out.println("==================================================");
    }
}
