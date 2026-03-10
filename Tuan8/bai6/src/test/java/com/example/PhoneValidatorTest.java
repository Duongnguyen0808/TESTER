package com.example;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * TDD Test class cho PhoneValidator.
 * RED → GREEN → REFACTOR
 * Bao gồm: 7 Basis Path + 10 Boundary test cases.
 */
public class PhoneValidatorTest {

    // ==================== BASIS PATH TESTS (CC=7) ====================

    @Test(description = "P1: phone=null → false (D1=True)")
    public void testP1_NullInput() {
        Assert.assertFalse(PhoneValidator.isValid(null));
    }

    @Test(description = "P1b: phone='' → false (D1=True, empty)")
    public void testP1b_EmptyInput() {
        Assert.assertFalse(PhoneValidator.isValid(""));
    }

    @Test(description = "P2: ký tự không hợp lệ → false (D2=True)")
    public void testP2_InvalidChars() {
        Assert.assertFalse(PhoneValidator.isValid("09abc12345"));
    }

    @Test(description = "P3: không bắt đầu 0 hoặc +84 → false (D3=F, D4=False)")
    public void testP3_NotStartWith0Or84() {
        Assert.assertFalse(PhoneValidator.isValid("1234567890"));
    }

    @Test(description = "P4: bắt đầu 0 nhưng sai độ dài → false (D5=False)")
    public void testP4_WrongLength() {
        Assert.assertFalse(PhoneValidator.isValid("091234567"));
    }

    @Test(description = "P5: 10 số nhưng đầu số 01 không hợp lệ → false (D6=False)")
    public void testP5_InvalidPrefix() {
        Assert.assertFalse(PhoneValidator.isValid("0112345678"));
    }

    @Test(description = "P6: hợp lệ bắt đầu bằng 0 → true (D6=True)")
    public void testP6_ValidStartWith0() {
        Assert.assertTrue(PhoneValidator.isValid("0912345678"));
    }

    @Test(description = "P7: hợp lệ bắt đầu bằng +84 → true (D3=True, chuẩn hóa)")
    public void testP7_ValidStartWith84() {
        Assert.assertTrue(PhoneValidator.isValid("+84912345678"));
    }

    // ==================== BOUNDARY TESTS ====================

    @Test(description = "B1: chuỗi toàn khoảng trắng → false")
    public void testB1_OnlySpaces() {
        Assert.assertFalse(PhoneValidator.isValid("   "));
    }

    @Test(description = "B2: có khoảng trắng hợp lệ → true")
    public void testB2_ValidWithSpaces() {
        Assert.assertTrue(PhoneValidator.isValid("091 234 5678"));
    }

    @Test(description = "B3: +84 nhưng đầu số sai → false")
    public void testB3_Plus84InvalidPrefix() {
        Assert.assertFalse(PhoneValidator.isValid("+84112345678"));
    }

    @Test(description = "B4: quá dài 11 số → false")
    public void testB4_TooLong() {
        Assert.assertFalse(PhoneValidator.isValid("09123456789"));
    }

    @Test(description = "B5: chỉ có +84 → false (quá ngắn)")
    public void testB5_OnlyPlus84() {
        Assert.assertFalse(PhoneValidator.isValid("+84"));
    }

    @Test(description = "B6: đầu số 03x hợp lệ → true")
    public void testB6_Prefix03() {
        Assert.assertTrue(PhoneValidator.isValid("0312345678"));
    }

    @Test(description = "B7: đầu số 05x hợp lệ → true")
    public void testB7_Prefix05() {
        Assert.assertTrue(PhoneValidator.isValid("0512345678"));
    }

    @Test(description = "B8: đầu số 07x hợp lệ → true")
    public void testB8_Prefix07() {
        Assert.assertTrue(PhoneValidator.isValid("0712345678"));
    }

    @Test(description = "B9: đầu số 08x hợp lệ → true")
    public void testB9_Prefix08() {
        Assert.assertTrue(PhoneValidator.isValid("0812345678"));
    }

    // ==================== DATA PROVIDER ====================

    @DataProvider(name = "phoneData")
    public Object[][] phoneData() {
        return new Object[][] {
            // Basis Paths
            { null,              false, "P1: null" },
            { "",                false, "P1b: empty" },
            { "09abc12345",      false, "P2: invalid chars" },
            { "1234567890",      false, "P3: not start 0/+84" },
            { "091234567",       false, "P4: wrong length" },
            { "0112345678",      false, "P5: invalid prefix 01" },
            { "0912345678",      true,  "P6: valid 09x" },
            { "+84912345678",    true,  "P7: valid +84" },
            // Boundary
            { "   ",             false, "B1: only spaces" },
            { "091 234 5678",    true,  "B2: valid with spaces" },
            { "+84112345678",    false, "B3: +84 invalid prefix" },
            { "09123456789",     false, "B4: too long" },
            { "+84",             false, "B5: only +84" },
            { "0312345678",      true,  "B6: prefix 03" },
            { "0512345678",      true,  "B7: prefix 05" },
            { "0712345678",      true,  "B8: prefix 07" },
            { "0812345678",      true,  "B9: prefix 08" },
        };
    }

    @Test(dataProvider = "phoneData", description = "DataProvider: tất cả BasisPath + Boundary")
    public void testPhoneValidator_DataProvider(String phone, boolean expected, String moTa) {
        Assert.assertEquals(PhoneValidator.isValid(phone), expected, "FAIL: " + moTa);
    }
}
