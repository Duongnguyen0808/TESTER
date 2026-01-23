import org.junit.Test;
import static org.junit.Assert.*;

public class Cau4Test {
    
    @Test
    public void testTinhDienTichBasic() {
        // Test case: Tính diện tích cơ bản
        // Hình chữ nhật 5x3 = 15
        Cau4 hcn = new Cau4(5.0, 3.0);
        assertEquals(15.0, hcn.tinhDienTich(), 0.0001);
    }
    
    @Test
    public void testTinhDienTichSquare() {
        // Test case: Hình vuông (trường hợp đặc biệt của hình chữ nhật)
        Cau4 hcn = new Cau4(4.0, 4.0);
        assertEquals(16.0, hcn.tinhDienTich(), 0.0001);
    }
    
    @Test
    public void testTinhDienTichDecimal() {
        // Test case: Kích thước thập phân
        Cau4 hcn = new Cau4(2.5, 3.5);
        assertEquals(8.75, hcn.tinhDienTich(), 0.0001);
    }
    
    @Test
    public void testTinhDienTichZero() {
        // Test case: Một chiều bằng 0
        Cau4 hcn = new Cau4(5.0, 0.0);
        assertEquals(0.0, hcn.tinhDienTich(), 0.0001);
    }
    
    @Test
    public void testTinhChuViBasic() {
        // Test case: Tính chu vi cơ bản
        // Hình chữ nhật 5x3, chu vi = 2*(5+3) = 16
        Cau4 hcn = new Cau4(5.0, 3.0);
        assertEquals(16.0, hcn.tinhChuVi(), 0.0001);
    }
    
    @Test
    public void testTinhChuViSquare() {
        // Test case: Hình vuông 4x4, chu vi = 16
        Cau4 hcn = new Cau4(4.0, 4.0);
        assertEquals(16.0, hcn.tinhChuVi(), 0.0001);
    }
    
    @Test
    public void testTinhChuViDecimal() {
        // Test case: Kích thước thập phân
        Cau4 hcn = new Cau4(2.5, 3.5);
        assertEquals(12.0, hcn.tinhChuVi(), 0.0001);
    }
    
    @Test
    public void testKiemTraGiaoNhauOverlapping() {
        // Test case: Hai hình chữ nhật giao nhau
        // Hình 1: (0,0) đến (5,3)
        // Hình 2: (2,1) đến (7,4)
        Cau4 hcn1 = new Cau4(5.0, 3.0);
        Cau4 hcn2 = new Cau4(5.0, 3.0);
        
        assertTrue(hcn1.kiemTraGiaoNhau(hcn2, 2.0, 1.0));
    }
    
    @Test
    public void testKiemTraGiaoNhauNotOverlapping() {
        // Test case: Hai hình chữ nhật KHÔNG giao nhau
        // Hình 1: (0,0) đến (5,3)
        // Hình 2: (10,10) đến (15,13) - xa hẳn
        Cau4 hcn1 = new Cau4(5.0, 3.0);
        Cau4 hcn2 = new Cau4(5.0, 3.0);
        
        assertFalse(hcn1.kiemTraGiaoNhau(hcn2, 10.0, 10.0));
    }
    
    @Test
    public void testKiemTraGiaoNhauTouching() {
        // Test case: Hai hình chữ nhật chạm nhau tại cạnh
        // Hình 1: (0,0) đến (5,3)
        // Hình 2: (5,0) đến (10,3) - chạm cạnh phải
        Cau4 hcn1 = new Cau4(5.0, 3.0);
        Cau4 hcn2 = new Cau4(5.0, 3.0);
        
        // Chạm nhau nhưng không giao nhau (không có vùng chung)
        assertFalse(hcn1.kiemTraGiaoNhau(hcn2, 5.0, 0.0));
    }
    
    @Test
    public void testKiemTraGiaoNhauPartialOverlap() {
        // Test case: Giao nhau một phần
        // Hình 1: (0,0) đến (4,4)
        // Hình 2: (3,3) đến (7,7) - giao nhau góc
        Cau4 hcn1 = new Cau4(4.0, 4.0);
        Cau4 hcn2 = new Cau4(4.0, 4.0);
        
        assertTrue(hcn1.kiemTraGiaoNhau(hcn2, 3.0, 3.0));
    }
    
    @Test
    public void testKiemTraGiaoNhauOneInsideAnother() {
        // Test case: Một hình nằm hoàn toàn trong hình kia
        // Hình 1: (0,0) đến (10,10)
        // Hình 2: (2,2) đến (4,4) - nằm trong hình 1
        Cau4 hcn1 = new Cau4(10.0, 10.0);
        Cau4 hcn2 = new Cau4(2.0, 2.0);
        
        assertTrue(hcn1.kiemTraGiaoNhau(hcn2, 2.0, 2.0));
    }
    
    @Test
    public void testKiemTraGiaoNhauNegativePosition() {
        // Test case: Vị trí âm (hình 2 ở bên trái/trên hình 1)
        // Hình 1: (0,0) đến (5,5)
        // Hình 2: (-3,-3) đến (2,2) - giao nhau
        Cau4 hcn1 = new Cau4(5.0, 5.0);
        Cau4 hcn2 = new Cau4(5.0, 5.0);
        
        assertTrue(hcn1.kiemTraGiaoNhau(hcn2, -3.0, -3.0));
    }
    
    @Test
    public void testKiemTraGiaoNhauCompletelyLeft() {
        // Test case: Hình 2 hoàn toàn ở bên trái hình 1
        Cau4 hcn1 = new Cau4(5.0, 5.0);
        Cau4 hcn2 = new Cau4(3.0, 3.0);
        
        assertFalse(hcn1.kiemTraGiaoNhau(hcn2, -5.0, 0.0));
    }
    
    @Test
    public void testKiemTraGiaoNhauCompletelyAbove() {
        // Test case: Hình 2 hoàn toàn ở phía trên hình 1
        Cau4 hcn1 = new Cau4(5.0, 5.0);
        Cau4 hcn2 = new Cau4(3.0, 3.0);
        
        assertFalse(hcn1.kiemTraGiaoNhau(hcn2, 0.0, -5.0));
    }
    
    @Test
    public void testGettersChieuDai() {
        // Test case: Kiểm tra getter chiều dài
        Cau4 hcn = new Cau4(7.5, 4.2);
        assertEquals(7.5, hcn.getChieuDai(), 0.0001);
    }
    
    @Test
    public void testGettersChieuRong() {
        // Test case: Kiểm tra getter chiều rộng
        Cau4 hcn = new Cau4(7.5, 4.2);
        assertEquals(4.2, hcn.getChieuRong(), 0.0001);
    }
}
