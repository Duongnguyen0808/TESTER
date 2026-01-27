package com.testing;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * EquivalenceClassTest - Test cases kiểm tra lớp tương đương
 * 
 * Áp dụng kỹ thuật Equivalence Class Partitioning (ECP)
 * Chia các đầu vào thành các lớp tương đương
 */
public class EquivalenceClassTest {
    
    private PaymentCalculator calculator;
    
    @Before
    public void setUp() {
        calculator = new PaymentCalculator();
    }
    
    @After
    public void tearDown() {
        calculator = null;
    }
    
    // ========== VALID EQUIVALENCE CLASSES ==========
    
    // Lớp 1: Trẻ em (0-17)
    @Test
    public void testValidClass_Child() {
        assertEquals("Lớp trẻ em: 10 tuổi", 50.0, 
            calculator.calculatePayment(10, PaymentCalculator.Gender.CHILD), 0.01);
    }
    
    // Lớp 2: Nam 18-35
    @Test
    public void testValidClass_Male_Young() {
        assertEquals("Lớp nam trẻ: 25 tuổi", 100.0, 
            calculator.calculatePayment(25, PaymentCalculator.Gender.MALE), 0.01);
    }
    
    // Lớp 3: Nam 36-50
    @Test
    public void testValidClass_Male_Middle() {
        assertEquals("Lớp nam trung niên: 43 tuổi", 120.0, 
            calculator.calculatePayment(43, PaymentCalculator.Gender.MALE), 0.01);
    }
    
    // Lớp 4: Nam 51-145
    @Test
    public void testValidClass_Male_Old() {
        assertEquals("Lớp nam cao tuổi: 80 tuổi", 140.0, 
            calculator.calculatePayment(80, PaymentCalculator.Gender.MALE), 0.01);
    }
    
    // Lớp 5: Nữ 18-35
    @Test
    public void testValidClass_Female_Young() {
        assertEquals("Lớp nữ trẻ: 25 tuổi", 80.0, 
            calculator.calculatePayment(25, PaymentCalculator.Gender.FEMALE), 0.01);
    }
    
    // Lớp 6: Nữ 36-50
    @Test
    public void testValidClass_Female_Middle() {
        assertEquals("Lớp nữ trung niên: 43 tuổi", 110.0, 
            calculator.calculatePayment(43, PaymentCalculator.Gender.FEMALE), 0.01);
    }
    
    // Lớp 7: Nữ 51-145
    @Test
    public void testValidClass_Female_Old() {
        assertEquals("Lớp nữ cao tuổi: 80 tuổi", 140.0, 
            calculator.calculatePayment(80, PaymentCalculator.Gender.FEMALE), 0.01);
    }
    
    // ========== INVALID EQUIVALENCE CLASSES ==========
    
    // Lớp không hợp lệ 1: Tuổi âm
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidClass_NegativeAge() {
        calculator.calculatePayment(-10, PaymentCalculator.Gender.MALE);
    }
    
    // Lớp không hợp lệ 2: Tuổi quá lớn
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidClass_TooOldAge() {
        calculator.calculatePayment(200, PaymentCalculator.Gender.FEMALE);
    }
}
