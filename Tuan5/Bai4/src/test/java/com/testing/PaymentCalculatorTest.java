package com.testing;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * PaymentCalculatorTest - Test cases với Test Fixture
 * 
 * Sử dụng:
 * - @BeforeClass: Setup một lần cho tất cả test cases
 * - @Before: Setup trước mỗi test case
 * - @After: Cleanup sau mỗi test case
 * - @AfterClass: Cleanup một lần sau tất cả test cases
 * - @Test: Đánh dấu test case
 */
public class PaymentCalculatorTest {
    
    private PaymentCalculator calculator;
    
    // Setup một lần cho tất cả test cases
    @BeforeClass
    public static void setUpClass() {
        System.out.println("=== BẮT ĐẦU TEST PAYMENT CALCULATOR ===");
        System.out.println("Khởi tạo test suite...\n");
    }
    
    // Setup trước mỗi test case
    @Before
    public void setUp() {
        calculator = new PaymentCalculator();
        System.out.println("Khởi tạo PaymentCalculator mới");
    }
    
    // Cleanup sau mỗi test case
    @After
    public void tearDown() {
        calculator = null;
        System.out.println("Dọn dẹp PaymentCalculator\n");
    }
    
    // Cleanup một lần sau tất cả test cases
    @AfterClass
    public static void tearDownClass() {
        System.out.println("\n=== KẾT THÚC TEST PAYMENT CALCULATOR ===");
    }
    
    // ========== TEST CASES CHO TRẺ EM (0-17 tuổi) ==========
    
    @Test
    public void testChild_Age0_ShouldReturn50() {
        System.out.println("Test: Trẻ em 0 tuổi");
        double result = calculator.calculatePayment(0, PaymentCalculator.Gender.CHILD);
        assertEquals("Trẻ em 0 tuổi phải trả 50 euro", 50.0, result, 0.01);
    }
    
    @Test
    public void testChild_Age10_ShouldReturn50() {
        System.out.println("Test: Trẻ em 10 tuổi");
        double result = calculator.calculatePayment(10, PaymentCalculator.Gender.CHILD);
        assertEquals("Trẻ em 10 tuổi phải trả 50 euro", 50.0, result, 0.01);
    }
    
    @Test
    public void testChild_Age17_ShouldReturn50() {
        System.out.println("Test: Trẻ em 17 tuổi (biên)");
        double result = calculator.calculatePayment(17, PaymentCalculator.Gender.CHILD);
        assertEquals("Trẻ em 17 tuổi phải trả 50 euro", 50.0, result, 0.01);
    }
    
    // ========== TEST CASES CHO NAM GIỚI ==========
    
    @Test
    public void testMale_Age18_ShouldReturn100() {
        System.out.println("Test: Nam 18 tuổi (biên dưới)");
        double result = calculator.calculatePayment(18, PaymentCalculator.Gender.MALE);
        assertEquals("Nam 18 tuổi phải trả 100 euro", 100.0, result, 0.01);
    }
    
    @Test
    public void testMale_Age25_ShouldReturn100() {
        System.out.println("Test: Nam 25 tuổi (giữa khoảng 18-35)");
        double result = calculator.calculatePayment(25, PaymentCalculator.Gender.MALE);
        assertEquals("Nam 25 tuổi phải trả 100 euro", 100.0, result, 0.01);
    }
    
    @Test
    public void testMale_Age35_ShouldReturn100() {
        System.out.println("Test: Nam 35 tuổi (biên trên)");
        double result = calculator.calculatePayment(35, PaymentCalculator.Gender.MALE);
        assertEquals("Nam 35 tuổi phải trả 100 euro", 100.0, result, 0.01);
    }
    
    @Test
    public void testMale_Age36_ShouldReturn120() {
        System.out.println("Test: Nam 36 tuổi (biên dưới)");
        double result = calculator.calculatePayment(36, PaymentCalculator.Gender.MALE);
        assertEquals("Nam 36 tuổi phải trả 120 euro", 120.0, result, 0.01);
    }
    
    @Test
    public void testMale_Age43_ShouldReturn120() {
        System.out.println("Test: Nam 43 tuổi (giữa khoảng 36-50)");
        double result = calculator.calculatePayment(43, PaymentCalculator.Gender.MALE);
        assertEquals("Nam 43 tuổi phải trả 120 euro", 120.0, result, 0.01);
    }
    
    @Test
    public void testMale_Age50_ShouldReturn120() {
        System.out.println("Test: Nam 50 tuổi (biên trên)");
        double result = calculator.calculatePayment(50, PaymentCalculator.Gender.MALE);
        assertEquals("Nam 50 tuổi phải trả 120 euro", 120.0, result, 0.01);
    }
    
    @Test
    public void testMale_Age51_ShouldReturn140() {
        System.out.println("Test: Nam 51 tuổi (biên dưới)");
        double result = calculator.calculatePayment(51, PaymentCalculator.Gender.MALE);
        assertEquals("Nam 51 tuổi phải trả 140 euro", 140.0, result, 0.01);
    }
    
    @Test
    public void testMale_Age100_ShouldReturn140() {
        System.out.println("Test: Nam 100 tuổi (giữa khoảng 51-145)");
        double result = calculator.calculatePayment(100, PaymentCalculator.Gender.MALE);
        assertEquals("Nam 100 tuổi phải trả 140 euro", 140.0, result, 0.01);
    }
    
    @Test
    public void testMale_Age145_ShouldReturn140() {
        System.out.println("Test: Nam 145 tuổi (biên trên)");
        double result = calculator.calculatePayment(145, PaymentCalculator.Gender.MALE);
        assertEquals("Nam 145 tuổi phải trả 140 euro", 140.0, result, 0.01);
    }
    
    // ========== TEST CASES CHO NỮ GIỚI ==========
    
    @Test
    public void testFemale_Age18_ShouldReturn80() {
        System.out.println("Test: Nữ 18 tuổi (biên dưới)");
        double result = calculator.calculatePayment(18, PaymentCalculator.Gender.FEMALE);
        assertEquals("Nữ 18 tuổi phải trả 80 euro", 80.0, result, 0.01);
    }
    
    @Test
    public void testFemale_Age25_ShouldReturn80() {
        System.out.println("Test: Nữ 25 tuổi (giữa khoảng 18-35)");
        double result = calculator.calculatePayment(25, PaymentCalculator.Gender.FEMALE);
        assertEquals("Nữ 25 tuổi phải trả 80 euro", 80.0, result, 0.01);
    }
    
    @Test
    public void testFemale_Age35_ShouldReturn80() {
        System.out.println("Test: Nữ 35 tuổi (biên trên)");
        double result = calculator.calculatePayment(35, PaymentCalculator.Gender.FEMALE);
        assertEquals("Nữ 35 tuổi phải trả 80 euro", 80.0, result, 0.01);
    }
    
    @Test
    public void testFemale_Age36_ShouldReturn110() {
        System.out.println("Test: Nữ 36 tuổi (biên dưới)");
        double result = calculator.calculatePayment(36, PaymentCalculator.Gender.FEMALE);
        assertEquals("Nữ 36 tuổi phải trả 110 euro", 110.0, result, 0.01);
    }
    
    @Test
    public void testFemale_Age43_ShouldReturn110() {
        System.out.println("Test: Nữ 43 tuổi (giữa khoảng 36-50)");
        double result = calculator.calculatePayment(43, PaymentCalculator.Gender.FEMALE);
        assertEquals("Nữ 43 tuổi phải trả 110 euro", 110.0, result, 0.01);
    }
    
    @Test
    public void testFemale_Age50_ShouldReturn110() {
        System.out.println("Test: Nữ 50 tuổi (biên trên)");
        double result = calculator.calculatePayment(50, PaymentCalculator.Gender.FEMALE);
        assertEquals("Nữ 50 tuổi phải trả 110 euro", 110.0, result, 0.01);
    }
    
    @Test
    public void testFemale_Age51_ShouldReturn140() {
        System.out.println("Test: Nữ 51 tuổi (biên dưới)");
        double result = calculator.calculatePayment(51, PaymentCalculator.Gender.FEMALE);
        assertEquals("Nữ 51 tuổi phải trả 140 euro", 140.0, result, 0.01);
    }
    
    @Test
    public void testFemale_Age100_ShouldReturn140() {
        System.out.println("Test: Nữ 100 tuổi (giữa khoảng 51-145)");
        double result = calculator.calculatePayment(100, PaymentCalculator.Gender.FEMALE);
        assertEquals("Nữ 100 tuổi phải trả 140 euro", 140.0, result, 0.01);
    }
    
    @Test
    public void testFemale_Age145_ShouldReturn140() {
        System.out.println("Test: Nữ 145 tuổi (biên trên)");
        double result = calculator.calculatePayment(145, PaymentCalculator.Gender.FEMALE);
        assertEquals("Nữ 145 tuổi phải trả 140 euro", 140.0, result, 0.01);
    }
    
    // ========== TEST CASES CHO TUỔI KHÔNG HỢP LỆ ==========
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAge_Negative_ShouldThrowException() {
        System.out.println("Test: Tuổi âm (không hợp lệ)");
        calculator.calculatePayment(-1, PaymentCalculator.Gender.MALE);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAge_TooOld_ShouldThrowException() {
        System.out.println("Test: Tuổi > 145 (không hợp lệ)");
        calculator.calculatePayment(146, PaymentCalculator.Gender.MALE);
    }
    
    // ========== TEST CASES CHO PHƯƠNG THỨC isValidAge ==========
    
    @Test
    public void testIsValidAge_ValidAge_ShouldReturnTrue() {
        System.out.println("Test: Kiểm tra tuổi hợp lệ");
        assertTrue("Tuổi 25 là hợp lệ", calculator.isValidAge(25));
        assertTrue("Tuổi 0 là hợp lệ", calculator.isValidAge(0));
        assertTrue("Tuổi 145 là hợp lệ", calculator.isValidAge(145));
    }
    
    @Test
    public void testIsValidAge_InvalidAge_ShouldReturnFalse() {
        System.out.println("Test: Kiểm tra tuổi không hợp lệ");
        assertFalse("Tuổi -1 không hợp lệ", calculator.isValidAge(-1));
        assertFalse("Tuổi 146 không hợp lệ", calculator.isValidAge(146));
    }
}
