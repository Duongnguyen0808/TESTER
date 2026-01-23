import java.util.ArrayList;
import java.util.List;

public class Cau5 {
    private List<HocVien> danhSachHocVien;

    public Cau5() {
        this.danhSachHocVien = new ArrayList<>();
    }

    // Thêm học viên vào danh sách
    public void themHocVien(HocVien hocVien) {
        if (hocVien != null) {
            danhSachHocVien.add(hocVien);
        }
    }

    // Lấy danh sách tất cả học viên
    public List<HocVien> getDanhSachHocVien() {
        return danhSachHocVien;
    }

    // Tìm học viên có thể nhận học bổng
    // Điều kiện: điểm trung bình >= 8.0 và không có môn nào < 5.0
    public List<HocVien> timHocVienNhanHocBong() {
        List<HocVien> ketQua = new ArrayList<>();
        
        for (HocVien hv : danhSachHocVien) {
            if (hv.duDieuKienHocBong()) {
                ketQua.add(hv);
            }
        }
        
        return ketQua;
    }

    // Tìm học viên theo mã số
    public HocVien timHocVienTheoMa(String maSo) {
        for (HocVien hv : danhSachHocVien) {
            if (hv.getMaSoHocVien().equals(maSo)) {
                return hv;
            }
        }
        return null;
    }

    // Tìm học viên theo quê quán
    public List<HocVien> timHocVienTheoQueQuan(String queQuan) {
        List<HocVien> ketQua = new ArrayList<>();
        
        for (HocVien hv : danhSachHocVien) {
            if (hv.getQueQuan().equalsIgnoreCase(queQuan)) {
                ketQua.add(hv);
            }
        }
        
        return ketQua;
    }

    // Đếm số lượng học viên
    public int demSoLuongHocVien() {
        return danhSachHocVien.size();
    }

    // Tính điểm trung bình của tất cả học viên
    public double tinhDiemTrungBinhChung() {
        if (danhSachHocVien.isEmpty()) {
            return 0.0;
        }
        
        double tongDiem = 0.0;
        for (HocVien hv : danhSachHocVien) {
            tongDiem += hv.getDiemTrungBinh();
        }
        
        return tongDiem / danhSachHocVien.size();
    }
}
