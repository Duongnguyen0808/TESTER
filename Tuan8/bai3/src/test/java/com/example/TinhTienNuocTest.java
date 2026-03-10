package com.example;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Bài 3.2 - Branch Coverage cho hàm tinhTienNuoc
 * 5 nút quyết định → 10 nhánh → 6 test case đạt 100% Branch Coverage
 */
public class TinhTienNuocTest {

    // TC1: soM3=0, "ho_ngheo" → return 0
    // Đường đi: Entry → N1(True) → Exit
    // Phủ nhánh: N1-True
    @Test
    public void testSoM3BangKhong_TraVe0() {
        double result = TinhTienNuoc.tinhTienNuoc(0, "ho_ngheo");
        Assert.assertEquals(result, 0.0,
            "soM3 = 0 phải trả về 0");
    }

    // TC2: soM3=5, "ho_ngheo" → 5 * 5000 = 25000
    // Đường đi: Entry → N1(F) → N2(True) → N6 → Exit
    // Phủ nhánh: N1-False, N2-True
    @Test
    public void testHoNgheo_5m3() {
        double result = TinhTienNuoc.tinhTienNuoc(5, "ho_ngheo");
        Assert.assertEquals(result, 25000.0,
            "Hộ nghèo 5m³ phải trả về 25,000");
    }

    // TC3: soM3=5, "dan_cu" → 5 * 7500 = 37500
    // Đường đi: Entry → N1(F) → N2(F) → N3(True) → N4(True) → N6 → Exit
    // Phủ nhánh: N2-False, N3-True, N4-True
    @Test
    public void testDanCu_5m3_Duoi10() {
        double result = TinhTienNuoc.tinhTienNuoc(5, "dan_cu");
        Assert.assertEquals(result, 37500.0,
            "Dân cư 5m³ (≤10) phải trả về 37,500");
    }

    // TC4: soM3=15, "dan_cu" → 15 * 9900 = 148500
    // Đường đi: Entry → N1(F) → N2(F) → N3(T) → N4(F) → N5(True) → N6 → Exit
    // Phủ nhánh: N4-False, N5-True
    @Test
    public void testDanCu_15m3_10Den20() {
        double result = TinhTienNuoc.tinhTienNuoc(15, "dan_cu");
        Assert.assertEquals(result, 148500.0,
            "Dân cư 15m³ (10-20) phải trả về 148,500");
    }

    // TC5: soM3=25, "dan_cu" → 25 * 11400 = 285000
    // Đường đi: Entry → N1(F) → N2(F) → N3(T) → N4(F) → N5(False) → N6 → Exit
    // Phủ nhánh: N5-False
    @Test
    public void testDanCu_25m3_Tren20() {
        double result = TinhTienNuoc.tinhTienNuoc(25, "dan_cu");
        Assert.assertEquals(result, 285000.0,
            "Dân cư 25m³ (>20) phải trả về 285,000");
    }

    // TC6: soM3=5, "kinh_doanh" → 5 * 22000 = 110000
    // Đường đi: Entry → N1(F) → N2(F) → N3(False) → N6 → Exit
    // Phủ nhánh: N3-False
    @Test
    public void testKinhDoanh_5m3() {
        double result = TinhTienNuoc.tinhTienNuoc(5, "kinh_doanh");
        Assert.assertEquals(result, 110000.0,
            "Kinh doanh 5m³ phải trả về 110,000");
    }
}
