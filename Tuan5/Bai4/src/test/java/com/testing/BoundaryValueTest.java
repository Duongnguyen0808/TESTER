package com.testing;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * BoundaryValueTest - Test cases kiểm tra giá trị biên
 * 
 * Áp dụng kỹ thuật Boundary Value Analysis (BVA)
 * Kiểm tra các giá trị tại biên của mỗi khoảng tuổi
 */
public class BoundaryValueTest {
    
    private PaymentCalculator calculator;
    
    @Before
    public void setUp() {
        calculator = new PaymentCalculator();
    }
    
    @After
    public void tearDown() {
        calculator = null;
    }
    
    // Test biên cho khoảng trẻ em (0-17)
    @Test
    public void testBoundary_Child_Lower() {
        assertEquals("Biên dưới: 0 tuổi", 50.0, 
            calculator.calculatePayment(0, PaymentCalculator.Gender.CHILD), 0.01);
    }
    
    @Test
    public void testBoundary_Child_Upper() {
        assertEquals("Biên trên: 17 tuổi", 50.0, 
            calculator.calculatePayment(17, PaymentCalculator.Gender.CHILD), 0.01);
    }
    
    // Test biên cho nam 18-35
    @Test
    public void testBoundary_Male_18to35_Lower() {
        assertEquals("Nam - Biên dưới: 18 tuổi", 100.0, 
            calculator.calculatePayment(18, PaymentCalculator.Gender.MALE), 0.01);
    }
    
    @Test
    public void testBoundary_Male_18to35_Upper() {
        assertEquals("Nam - Biên trên: 35 tuổi", 100.0, 
            calculator.calculatePayment(35, PaymentCalculator.Gender.MALE), 0.01);
    }
    
    // Test biên cho nam 36-50
    @Test
    public void testBoundary_Male_36to50_Lower() {
        assertEquals("Nam - Biên dưới: 36 tuổi", 120.0, 
            calculator.calculatePayment(36, PaymentCalculator.Gender.MALE), 0.01);
    }
    
    @Test
    public void testBoundary_Male_36to50_Upper() {
        assertEquals("Nam - Biên trên: 50 tuổi", 120.0, 
            calculator.calculatePayment(50, PaymentCalculator.Gender.MALE), 0.01);
    }
    
    // Test biên cho nam 51-145
    @Test
    public void testBoundary_Male_51to145_Lower() {
        assertEquals("Nam - Biên dưới: 51 tuổi", 140.0, 
            calculator.calculatePayment(51, PaymentCalculator.Gender.MALE), 0.01);
    }
    
    @Test
    public void testBoundary_Male_51to145_Upper() {
        assertEquals("Nam - Biên trên: 145 tuổi", 140.0, 
            calculator.calculatePayment(145, PaymentCalculator.Gender.MALE), 0.01);
    }
    
    // Test biên cho nữ 18-35
    @Test
    public void testBoundary_Female_18to35_Lower() {
        assertEquals("Nữ - Biên dưới: 18 tuổi", 80.0, 
            calculator.calculatePayment(18, PaymentCalculator.Gender.FEMALE), 0.01);
    }
    
    @Test
    public void testBoundary_Female_18to35_Upper() {
        assertEquals("Nữ - Biên trên: 35 tuổi", 80.0, 
            calculator.calculatePayment(35, PaymentCalculator.Gender.FEMALE), 0.01);
    }
    
    // Test biên cho nữ 36-50
    @Test
    public void testBoundary_Female_36to50_Lower() {
        assertEquals("Nữ - Biên dưới: 36 tuổi", 110.0, 
            calculator.calculatePayment(36, PaymentCalculator.Gender.FEMALE), 0.01);
    }
    
    @Test
    public void testBoundary_Female_36to50_Upper() {
        assertEquals("Nữ - Biên trên: 50 tuổi", 110.0, 
            calculator.calculatePayment(50, PaymentCalculator.Gender.FEMALE), 0.01);
    }
    
    // Test biên cho nữ 51-145
    @Test
    public void testBoundary_Female_51to145_Lower() {
        assertEquals("Nữ - Biên dưới: 51 tuổi", 140.0, 
            calculator.calculatePayment(51, PaymentCalculator.Gender.FEMALE), 0.01);
    }
    
    @Test
    public void testBoundary_Female_51to145_Upper() {
        assertEquals("Nữ - Biên trên: 145 tuổi", 140.0, 
            calculator.calculatePayment(145, PaymentCalculator.Gender.FEMALE), 0.01);
    }
}
