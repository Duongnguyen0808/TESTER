package com.example;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Bài 5.2 - Viết test case TestNG từ phân tích MC/DC
 * Biểu thức: (A && B) && (C || D)
 *   A = tuoi >= 22
 *   B = thuNhap >= 10_000_000
 *   C = coTaiSanBaoLanh
 *   D = dienTinDung >= 700
 * MC/DC cần 5 test case (n+1 = 4+1 = 5)
 */
public class VayVonMCDCTest {

    // ====== TEST RIÊNG CHO TỪNG CẶP MC/DC ======

    // TC1 (Row 2): A=T, B=T, C=T, D=F → true (base cho A, B, C)
    @Test(description = "MC/DC Base: tuoi=25(>=22), thuNhap=15M(>=10M), coTaiSan=true, dienTD=600(<700) → TRUE")
    public void testMCDC_Base_TatCaDieuKienCoBanVaBaoDam() {
        boolean result = VayVon.duDieuKienVay(25, 15_000_000, true, 600);
        Assert.assertTrue(result,
            "Tuổi 25, thu nhập 15M, có tài sản, tín dụng 600 → phải được vay (dieuKienCoBan=T, C=T)");
    }

    // TC2 (Row 3): A=T, B=T, C=F, D=T → true (base cho D)
    @Test(description = "MC/DC D-True: tuoi=25, thuNhap=15M, coTaiSan=false, dienTD=750(>=700) → TRUE")
    public void testMCDC_DienTinDungDocLap_CaoDu() {
        boolean result = VayVon.duDieuKienVay(25, 15_000_000, false, 750);
        Assert.assertTrue(result,
            "Tuổi 25, thu nhập 15M, không tài sản, tín dụng 750 → phải được vay (D=T bù cho C=F)");
    }

    // TC3 (Row 4): A=T, B=T, C=F, D=F → false (pair cho C và D)
    @Test(description = "MC/DC C-False & D-False: tuoi=25, thuNhap=15M, coTaiSan=false, dienTD=600 → FALSE")
    public void testMCDC_KhongTaiSan_TinDungThap() {
        boolean result = VayVon.duDieuKienVay(25, 15_000_000, false, 600);
        Assert.assertFalse(result,
            "Tuổi 25, thu nhập 15M, không tài sản, tín dụng 600 → không được vay (C=F, D=F)");
    }

    // TC4 (Row 6): A=T, B=F, C=T, D=F → false (pair cho B)
    @Test(description = "MC/DC B-DocLap: tuoi=25, thuNhap=5M(<10M), coTaiSan=true, dienTD=600 → FALSE")
    public void testMCDC_ThuNhapDocLap_ThapHon10M() {
        boolean result = VayVon.duDieuKienVay(25, 5_000_000, true, 600);
        Assert.assertFalse(result,
            "Tuổi 25, thu nhập 5M, có tài sản, tín dụng 600 → không được vay (B=F làm dieuKienCoBan=F)");
    }

    // TC5 (Row 10): A=F, B=T, C=T, D=F → false (pair cho A)
    @Test(description = "MC/DC A-DocLap: tuoi=20(<22), thuNhap=15M, coTaiSan=true, dienTD=600 → FALSE")
    public void testMCDC_TuoiDocLap_ThapHon22() {
        boolean result = VayVon.duDieuKienVay(20, 15_000_000, true, 600);
        Assert.assertFalse(result,
            "Tuổi 20, thu nhập 15M, có tài sản, tín dụng 600 → không được vay (A=F làm dieuKienCoBan=F)");
    }

    // ====== DATA PROVIDER KẾT HỢP TẤT CẢ MC/DC ======

    @DataProvider(name = "mcdcData")
    public Object[][] mcdcDataProvider() {
        return new Object[][] {
            // { tuoi, thuNhap, coTaiSan, dienTinDung, expectedResult, moTa }
            { 25, 15_000_000.0, true,  600, true,  "Row2: Base (A=T,B=T,C=T,D=F) → TRUE" },
            { 25, 15_000_000.0, false, 750, true,  "Row3: D-True base (A=T,B=T,C=F,D=T) → TRUE" },
            { 25, 15_000_000.0, false, 600, false, "Row4: C-F & D-F pair (A=T,B=T,C=F,D=F) → FALSE" },
            { 25,  5_000_000.0, true,  600, false, "Row6: B-DocLap pair (A=T,B=F,C=T,D=F) → FALSE" },
            { 20, 15_000_000.0, true,  600, false, "Row10: A-DocLap pair (A=F,B=T,C=T,D=F) → FALSE" },
        };
    }

    @Test(dataProvider = "mcdcData",
          description = "MC/DC DataProvider: chạy tất cả 5 TC từ bảng MC/DC")
    public void testMCDC_DataProvider(int tuoi, double thuNhap,
            boolean coTaiSan, int dienTinDung, boolean expected, String moTa) {
        boolean result = VayVon.duDieuKienVay(tuoi, thuNhap, coTaiSan, dienTinDung);
        Assert.assertEquals(result, expected, moTa);
    }
}
