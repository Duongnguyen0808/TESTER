import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class Cau5Test {
    
    private Cau5 quanLy;
    private HocVien hv1, hv2, hv3, hv4, hv5;
    
    @Before
    public void setUp() {
        quanLy = new Cau5();
        
        // Tạo dữ liệu test
        hv1 = new HocVien("HV001", "Nguyen Van A", "Ha Noi", 8.5);
        hv2 = new HocVien("HV002", "Tran Thi B", "Ho Chi Minh", 7.5);
        hv3 = new HocVien("HV003", "Le Van C", "Da Nang", 9.0);
        hv4 = new HocVien("HV004", "Pham Thi D", "Ha Noi", 6.5);
        hv5 = new HocVien("HV005", "Hoang Van E", "Hue", 8.2);
    }
    
    @Test
    public void testThemHocVien() {
        // Test thêm học viên vào danh sách
        quanLy.themHocVien(hv1);
        assertEquals(1, quanLy.demSoLuongHocVien());
        
        quanLy.themHocVien(hv2);
        assertEquals(2, quanLy.demSoLuongHocVien());
    }
    
    @Test
    public void testThemHocVienNull() {
        // Test thêm học viên null
        quanLy.themHocVien(null);
        assertEquals(0, quanLy.demSoLuongHocVien());
    }
    
    @Test
    public void testTimHocVienNhanHocBong() {
        // Test tìm học viên đủ điều kiện nhận học bổng (điểm >= 8.0)
        quanLy.themHocVien(hv1); // 8.5 - đủ điều kiện
        quanLy.themHocVien(hv2); // 7.5 - không đủ
        quanLy.themHocVien(hv3); // 9.0 - đủ điều kiện
        quanLy.themHocVien(hv4); // 6.5 - không đủ
        quanLy.themHocVien(hv5); // 8.2 - đủ điều kiện
        
        List<HocVien> ketQua = quanLy.timHocVienNhanHocBong();
        
        assertEquals(3, ketQua.size());
        assertTrue(ketQua.contains(hv1));
        assertTrue(ketQua.contains(hv3));
        assertTrue(ketQua.contains(hv5));
    }
    
    @Test
    public void testTimHocVienNhanHocBongKhongCo() {
        // Test khi không có học viên nào đủ điều kiện
        quanLy.themHocVien(hv2); // 7.5
        quanLy.themHocVien(hv4); // 6.5
        
        List<HocVien> ketQua = quanLy.timHocVienNhanHocBong();
        
        assertEquals(0, ketQua.size());
    }
    
    @Test
    public void testTimHocVienNhanHocBongTatCa() {
        // Test khi tất cả học viên đều đủ điều kiện
        quanLy.themHocVien(hv1); // 8.5
        quanLy.themHocVien(hv3); // 9.0
        quanLy.themHocVien(hv5); // 8.2
        
        List<HocVien> ketQua = quanLy.timHocVienNhanHocBong();
        
        assertEquals(3, ketQua.size());
    }
    
    @Test
    public void testTimHocVienTheoMa() {
        // Test tìm học viên theo mã số
        quanLy.themHocVien(hv1);
        quanLy.themHocVien(hv2);
        quanLy.themHocVien(hv3);
        
        HocVien timThay = quanLy.timHocVienTheoMa("HV002");
        assertNotNull(timThay);
        assertEquals("Tran Thi B", timThay.getHoTen());
    }
    
    @Test
    public void testTimHocVienTheoMaKhongTonTai() {
        // Test tìm học viên với mã không tồn tại
        quanLy.themHocVien(hv1);
        
        HocVien timThay = quanLy.timHocVienTheoMa("HV999");
        assertNull(timThay);
    }
    
    @Test
    public void testTimHocVienTheoQueQuan() {
        // Test tìm học viên theo quê quán
        quanLy.themHocVien(hv1); // Ha Noi
        quanLy.themHocVien(hv2); // Ho Chi Minh
        quanLy.themHocVien(hv3); // Da Nang
        quanLy.themHocVien(hv4); // Ha Noi
        
        List<HocVien> ketQua = quanLy.timHocVienTheoQueQuan("Ha Noi");
        
        assertEquals(2, ketQua.size());
        assertTrue(ketQua.contains(hv1));
        assertTrue(ketQua.contains(hv4));
    }
    
    @Test
    public void testTimHocVienTheoQueQuanKhongPhanBietChuHoaChuThuong() {
        // Test tìm kiếm không phân biệt chữ hoa/thường
        quanLy.themHocVien(hv1); // Ha Noi
        
        List<HocVien> ketQua = quanLy.timHocVienTheoQueQuan("ha noi");
        
        assertEquals(1, ketQua.size());
    }
    
    @Test
    public void testTimHocVienTheoQueQuanKhongCo() {
        // Test tìm quê quán không có học viên nào
        quanLy.themHocVien(hv1);
        quanLy.themHocVien(hv2);
        
        List<HocVien> ketQua = quanLy.timHocVienTheoQueQuan("Can Tho");
        
        assertEquals(0, ketQua.size());
    }
    
    @Test
    public void testDemSoLuongHocVien() {
        // Test đếm số lượng học viên
        assertEquals(0, quanLy.demSoLuongHocVien());
        
        quanLy.themHocVien(hv1);
        assertEquals(1, quanLy.demSoLuongHocVien());
        
        quanLy.themHocVien(hv2);
        quanLy.themHocVien(hv3);
        assertEquals(3, quanLy.demSoLuongHocVien());
    }
    
    @Test
    public void testTinhDiemTrungBinhChung() {
        // Test tính điểm trung bình chung
        quanLy.themHocVien(hv1); // 8.5
        quanLy.themHocVien(hv2); // 7.5
        quanLy.themHocVien(hv3); // 9.0
        
        // (8.5 + 7.5 + 9.0) / 3 = 25.0 / 3 = 8.333...
        double dtb = quanLy.tinhDiemTrungBinhChung();
        assertEquals(8.333, dtb, 0.01);
    }
    
    @Test
    public void testTinhDiemTrungBinhChungDanhSachRong() {
        // Test tính điểm trung bình khi danh sách rỗng
        double dtb = quanLy.tinhDiemTrungBinhChung();
        assertEquals(0.0, dtb, 0.001);
    }
    
    @Test
    public void testHocVienDuDieuKienHocBong() {
        // Test phương thức kiểm tra điều kiện học bổng của HocVien
        assertTrue(hv1.duDieuKienHocBong()); // 8.5 >= 8.0
        assertFalse(hv2.duDieuKienHocBong()); // 7.5 < 8.0
        assertTrue(hv3.duDieuKienHocBong()); // 9.0 >= 8.0
        assertFalse(hv4.duDieuKienHocBong()); // 6.5 < 8.0
        assertTrue(hv5.duDieuKienHocBong()); // 8.2 >= 8.0
    }
    
    @Test
    public void testHocVienDiemBien() {
        // Test với điểm biên 8.0
        HocVien hvBien = new HocVien("HV006", "Test Student", "Ha Noi", 8.0);
        assertTrue(hvBien.duDieuKienHocBong()); // 8.0 >= 8.0
        
        HocVien hvDuoi = new HocVien("HV007", "Test Student 2", "Ha Noi", 7.9);
        assertFalse(hvDuoi.duDieuKienHocBong()); // 7.9 < 8.0
    }
    
    @Test
    public void testGetDanhSachHocVien() {
        // Test lấy danh sách học viên
        quanLy.themHocVien(hv1);
        quanLy.themHocVien(hv2);
        
        List<HocVien> danhSach = quanLy.getDanhSachHocVien();
        
        assertEquals(2, danhSach.size());
        assertTrue(danhSach.contains(hv1));
        assertTrue(danhSach.contains(hv2));
    }
    
    @Test
    public void testHocVienGetters() {
        // Test các getter methods của HocVien
        assertEquals("HV001", hv1.getMaSoHocVien());
        assertEquals("Nguyen Van A", hv1.getHoTen());
        assertEquals("Ha Noi", hv1.getQueQuan());
        assertEquals(8.5, hv1.getDiemTrungBinh(), 0.001);
    }
    
    @Test
    public void testTimHocVienNhanHocBongDiem10() {
        // Test với học viên có điểm tối đa
        HocVien hvXuatSac = new HocVien("HV010", "Hoc Sinh Xuat Sac", "Ha Noi", 10.0);
        quanLy.themHocVien(hvXuatSac);
        
        List<HocVien> ketQua = quanLy.timHocVienNhanHocBong();
        
        assertEquals(1, ketQua.size());
        assertEquals(10.0, ketQua.get(0).getDiemTrungBinh(), 0.001);
    }
}
