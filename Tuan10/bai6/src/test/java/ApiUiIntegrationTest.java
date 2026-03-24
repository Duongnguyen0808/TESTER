import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ApiUiIntegrationTest {

    private WebDriver driver;
    private String apiToken;
    private boolean isApiAlive;

    // Thiết lập tổng đài cho cả Selenium Driver lẫn RestAssured BaseURI
    @BeforeClass
    public void setupAll() {
        RestAssured.baseURI = "https://reqres.in";
        
        // Setup WebDriver tự động qua WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(); // Chạy có bật trình duyệt UI cho sinh động 
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait 10s cho chắc chắn
        driver.manage().window().maximize();
    }

    // ==========================================
    // PHẦN A: API Precondition -> UI Verification
    // ==========================================
    
    /**
     * Yêu cầu: "dùng dependsOnMethods để SKIP test UI". 
     * MỘT SỰ LƯU Ý TRONG TESTNG: dependsOnMethods CHỈ móc nối được giữa 2 method @Test với nhau (không dùng được trên @BeforeMethod).
     * Do đó, mình thiết lập bước lấy Token (Precondition) này thành 1 hàm @Test chạy đầu tiên.
     */
    @Test(priority = 1, description = "Tích hợp API: Gọi API login lấy Token làm precondition")
    public void apiLoginPrecondition() {
        System.out.println("\n=== Đây là bước API check (API Login Precondition) ===");
        
        java.util.Map<String, String> payload = new java.util.HashMap<>();
        payload.put("email", "eve.holt@reqres.in");
        payload.put("password", "cityslicka");

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(payload)
                .post("/api/login");

        // Gọi POST /api/login, nếu fail -> sẽ bung lỗi
        if (response.statusCode() != 200) {
            Assert.fail("API Login Precondition thất bại với status code: " + response.statusCode());
        }

        // Lấy token và log ra console
        apiToken = response.jsonPath().getString("token");
        System.out.println("Token xác nhận lấy được là: " + apiToken);
        Assert.assertNotNull(apiToken, "Token không được null");
    }

    // Phụ thuộc trực tiếp vào apiLoginPrecondition
    @Test(priority = 2, dependsOnMethods = "apiLoginPrecondition", description = "Tích hợp UI: Đăng nhập Saucedemo và Verify Dashboard")
    public void uiVerificationAfterLogin() {
        System.out.println("=== Đây là bước UI action (Login saucedemo) ===");
        driver.get("https://www.saucedemo.com/");
        
        // Login manual form (không dùng inject cookie theo đề bài)
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        System.out.println("=== Đây là assertion (Verify URL và Title) ===");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory"), "URL sau đăng nhập không chứa chữ nhánh 'inventory'");
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title trang web hiển thị sai tiêu chuẩn");
    }

    // ==========================================
    // PHẦN B: Luồng tích hợp đầy đủ
    // ==========================================
    
    @Test(priority = 3, description = "Tích hợp toàn trình: Check API sống, nếu fail SKIP UI, nếu pass tiến hành thêm vào giỏ hàng")
    public void fullIntegrationFlow() {
        System.out.println("\n=== Đây là bước API check (Xác nhận hệ thống Alive) ===");
        Response apiResponse = RestAssured.given().get("/api/users");
        
        // Lưu kết quả vào biến isApiAlive
        isApiAlive = (apiResponse.statusCode() == 200);
        
        if (!isApiAlive) {
            // Ném SkipException thẳng tay để bỏ qua toàn bộ phần test UI phía sau (Skip Test)
            throw new SkipException("Bỏ qua Test! API reqres.in không hoạt động (isApiAlive = false).");
        }

        System.out.println("=== Đây là bước UI action (Xử lý toàn bộ luồng Giỏ Hàng) ===");
        // Khởi động lại trang web sạch sẽ do Test trước đã login
        driver.get("https://www.saucedemo.com/");
        
        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Thêm 2 sản phẩm (Backpack & Bike Light)
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        System.out.println("=== Đây là assertion (Kiểm tra badge của giỏ hàng) ===");
        WebElement badge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(badge.getText(), "2", "Badge hiển thị bên ngoài giỏ hàng không về 2 món");

        System.out.println("=== Đây là bước UI action (Đi thẳng vào trong Giỏ Cart) ===");
        driver.findElement(By.className("shopping_cart_link")).click();

        System.out.println("=== Đây là assertion (Check lại List món hàng) ===");
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        Assert.assertEquals(cartItems.size(), 2, "List thực tế chui trong Cart không khớp số lượng 2 món");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
