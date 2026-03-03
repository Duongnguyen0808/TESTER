package com.shopvn.model;

/**
 * Model đại diện cho Form Đăng Ký Tài Khoản – ShopVN.vn
 */
public class RegistrationForm {
    private String hoTen;           // Họ và tên (*)
    private String tenDangNhap;     // Tên đăng nhập (*)
    private String email;           // Email (*)
    private String soDienThoai;     // Số điện thoại (*)
    private String matKhau;         // Mật khẩu (*)
    private String xacNhanMatKhau;  // Xác nhận mật khẩu (*)
    private String ngaySinh;        // Ngày sinh (dd/mm/yyyy)
    private String gioiTinh;        // Giới tính
    private String maGioiThieu;     // Mã giới thiệu
    private boolean dongYDieuKhoan; // Đồng ý Điều khoản (*)

    public RegistrationForm() {}

    // Getters and Setters
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getXacNhanMatKhau() { return xacNhanMatKhau; }
    public void setXacNhanMatKhau(String xacNhanMatKhau) { this.xacNhanMatKhau = xacNhanMatKhau; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getMaGioiThieu() { return maGioiThieu; }
    public void setMaGioiThieu(String maGioiThieu) { this.maGioiThieu = maGioiThieu; }

    public boolean isDongYDieuKhoan() { return dongYDieuKhoan; }
    public void setDongYDieuKhoan(boolean dongYDieuKhoan) { this.dongYDieuKhoan = dongYDieuKhoan; }
}
