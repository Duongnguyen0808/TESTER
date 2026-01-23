public class HocVien {
    private String maSoHocVien;
    private String hoTen;
    private String queQuan;
    private double diemTrungBinh;

    public HocVien(String maSoHocVien, String hoTen, String queQuan, double diemTrungBinh) {
        this.maSoHocVien = maSoHocVien;
        this.hoTen = hoTen;
        this.queQuan = queQuan;
        this.diemTrungBinh = diemTrungBinh;
    }

    public String getMaSoHocVien() {
        return maSoHocVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    // Kiểm tra học viên có đủ điều kiện nhận học bổng không
    public boolean duDieuKienHocBong() {
        return diemTrungBinh >= 8.0;
    }

    @Override
    public String toString() {
        return "HocVien{" +
                "maSoHocVien='" + maSoHocVien + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", queQuan='" + queQuan + '\'' +
                ", diemTrungBinh=" + diemTrungBinh +
                '}';
    }
}
