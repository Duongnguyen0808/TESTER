package com.example;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Bài 4.2 - Viết test case TestNG từ Basis Path
 * CC = 7 decisions + 1 = 8 → 8 đường cơ sở → 8 test method
 */
public class PhiShipBasisPathTest {

    // Path 1: D1=True → throw Exception
    // N1(T) → N2 → Exit
    @Test(description = "Path 1: Trọng lượng không hợp lệ (trongLuong <= 0)")
    public void testPath1_InvalidWeight() {
        Assert.assertThrows(IllegalArgumentException.class,
            () -> PhiShip.tinhPhiShip(-1, "noi_thanh", false));
    }

    // Path 2: D1=F, D2=T, D3=F, D7=F
    // N1(F) → N3(T) → N4(F) → N11(F) → N13 → Exit
    // Nội thành, trọng lượng <= 5kg, không member
    @Test(description = "Path 2: Nội thành, <= 5kg, không member")
    public void testPath2_NoiThanhNheKhongMember() {
        double expected = 15000;
        Assert.assertEquals(PhiShip.tinhPhiShip(3, "noi_thanh", false), expected, 0.01);
    }

    // Path 3: D1=F, D2=T, D3=T, D7=F
    // N1(F) → N3(T) → N4(T) → N5 → N11(F) → N13 → Exit
    // Nội thành, trọng lượng > 5kg, không member
    @Test(description = "Path 3: Nội thành, > 5kg, không member")
    public void testPath3_NoiThanhNangKhongMember() {
        // phi = 15000 + (8-5)*2000 = 15000 + 6000 = 21000
        double expected = 21000;
        Assert.assertEquals(PhiShip.tinhPhiShip(8, "noi_thanh", false), expected, 0.01);
    }

    // Path 4: D1=F, D2=F, D4=T, D5=F, D7=F
    // N1(F) → N3(F) → N6(T) → N7(F) → N11(F) → N13 → Exit
    // Ngoại thành, trọng lượng <= 3kg, không member
    @Test(description = "Path 4: Ngoại thành, <= 3kg, không member")
    public void testPath4_NgoaiThanhNheKhongMember() {
        double expected = 25000;
        Assert.assertEquals(PhiShip.tinhPhiShip(2, "ngoai_thanh", false), expected, 0.01);
    }

    // Path 5: D1=F, D2=F, D4=T, D5=T, D7=F
    // N1(F) → N3(F) → N6(T) → N7(T) → N8 → N11(F) → N13 → Exit
    // Ngoại thành, trọng lượng > 3kg, không member
    @Test(description = "Path 5: Ngoại thành, > 3kg, không member")
    public void testPath5_NgoaiThanhNangKhongMember() {
        // phi = 25000 + (5-3)*3000 = 25000 + 6000 = 31000
        double expected = 31000;
        Assert.assertEquals(PhiShip.tinhPhiShip(5, "ngoai_thanh", false), expected, 0.01);
    }

    // Path 6: D1=F, D2=F, D4=F, D6=F, D7=F
    // N1(F) → N3(F) → N6(F) → N9(F) → N11(F) → N13 → Exit
    // Tỉnh khác, trọng lượng <= 2kg, không member
    @Test(description = "Path 6: Tỉnh khác, <= 2kg, không member")
    public void testPath6_TinhKhacNheKhongMember() {
        double expected = 50000;
        Assert.assertEquals(PhiShip.tinhPhiShip(1, "tinh_khac", false), expected, 0.01);
    }

    // Path 7: D1=F, D2=F, D4=F, D6=T, D7=F
    // N1(F) → N3(F) → N6(F) → N9(T) → N10 → N11(F) → N13 → Exit
    // Tỉnh khác, trọng lượng > 2kg, không member
    @Test(description = "Path 7: Tỉnh khác, > 2kg, không member")
    public void testPath7_TinhKhacNangKhongMember() {
        // phi = 50000 + (4-2)*5000 = 50000 + 10000 = 60000
        double expected = 60000;
        Assert.assertEquals(PhiShip.tinhPhiShip(4, "tinh_khac", false), expected, 0.01);
    }

    // Path 8: D1=F, D2=F, D4=F, D6=F, D7=T
    // N1(F) → N3(F) → N6(F) → N9(F) → N11(T) → N12 → N13 → Exit
    // Tỉnh khác, trọng lượng <= 2kg, là member → giảm 10%
    @Test(description = "Path 8: Tỉnh khác, <= 2kg, là member (giảm 10%)")
    public void testPath8_TinhKhacNheMember() {
        // phi = 50000 * 0.9 = 45000
        double expected = 45000;
        Assert.assertEquals(PhiShip.tinhPhiShip(1, "tinh_khac", true), expected, 0.01);
    }
}
