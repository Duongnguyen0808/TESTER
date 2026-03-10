package com.example;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Bài 3.1 - Branch Coverage cho hàm xepLoai
 * 5 nút quyết định → 10 nhánh → 6 test case đạt 100% Branch Coverage
 */
public class XepLoaiTest {

    // TC1: diemTB = -1 → "Diem khong hop le"
    // Đường đi: Entry → N1(True) → Exit
    // Phủ nhánh: N1-True
    @Test
    public void testDiemAm_DiemKhongHopLe() {
        String result = XepLoai.xepLoai(-1, false);
        Assert.assertEquals(result, "Diem khong hop le",
            "Điểm âm phải trả về 'Diem khong hop le'");
    }

    // TC2: diemTB = 9 → "Gioi"
    // Đường đi: Entry → N1(False) → N2(True) → Exit
    // Phủ nhánh: N1-False, N2-True
    @Test
    public void testDiem9_XepLoaiGioi() {
        String result = XepLoai.xepLoai(9, false);
        Assert.assertEquals(result, "Gioi",
            "Điểm 9 phải xếp loại 'Gioi'");
    }

    // TC3: diemTB = 7 → "Kha"
    // Đường đi: Entry → N1(F) → N2(F) → N3(True) → Exit
    // Phủ nhánh: N2-False, N3-True
    @Test
    public void testDiem7_XepLoaiKha() {
        String result = XepLoai.xepLoai(7, false);
        Assert.assertEquals(result, "Kha",
            "Điểm 7 phải xếp loại 'Kha'");
    }

    // TC4: diemTB = 6 → "Trung Binh"
    // Đường đi: Entry → N1(F) → N2(F) → N3(F) → N4(True) → Exit
    // Phủ nhánh: N3-False, N4-True
    @Test
    public void testDiem6_XepLoaiTrungBinh() {
        String result = XepLoai.xepLoai(6, false);
        Assert.assertEquals(result, "Trung Binh",
            "Điểm 6 phải xếp loại 'Trung Binh'");
    }

    // TC5: diemTB = 3, coThiLai = true → "Thi lai"
    // Đường đi: Entry → N1(F) → N2(F) → N3(F) → N4(F) → N5(True) → Exit
    // Phủ nhánh: N4-False, N5-True
    @Test
    public void testDiem3CoThiLai_ThiLai() {
        String result = XepLoai.xepLoai(3, true);
        Assert.assertEquals(result, "Thi lai",
            "Điểm 3 có thi lại phải trả về 'Thi lai'");
    }

    // TC6: diemTB = 3, coThiLai = false → "Yeu - Hoc lai"
    // Đường đi: Entry → N1(F) → N2(F) → N3(F) → N4(F) → N5(False) → Exit
    // Phủ nhánh: N5-False
    @Test
    public void testDiem3KhongThiLai_YeuHocLai() {
        String result = XepLoai.xepLoai(3, false);
        Assert.assertEquals(result, "Yeu - Hoc lai",
            "Điểm 3 không thi lại phải trả về 'Yeu - Hoc lai'");
    }
}
