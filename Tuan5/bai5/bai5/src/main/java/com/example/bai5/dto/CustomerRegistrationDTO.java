package com.example.bai5.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class CustomerRegistrationDTO {
    
    @NotBlank(message = "Mã khách hàng không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$", message = "Mã khách hàng phải từ 6-10 ký tự, chỉ chứa chữ cái và số")
    private String maKhachHang;
    
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 5, max = 50, message = "Họ và tên phải từ 5-50 ký tự")
    private String hoTen;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0[0-9]{9,11}$", message = "Số điện thoại phải từ 10-12 số và bắt đầu bằng số 0")
    private String soDienThoai;
    
    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String diaChi;
    
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String matKhau;
    
    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String xacNhanMatKhau;
    
    private LocalDate ngaySinh;
    
    private String gioiTinh;
    
    @AssertTrue(message = "Bạn phải đồng ý với các điều khoản dịch vụ")
    private Boolean dongYDieuKhoan = false;
    
    // Getters and Setters
    public String getMaKhachHang() {
        return maKhachHang;
    }
    
    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
    
    public String getHoTen() {
        return hoTen;
    }
    
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSoDienThoai() {
        return soDienThoai;
    }
    
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    
    public String getDiaChi() {
        return diaChi;
    }
    
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    public String getMatKhau() {
        return matKhau;
    }
    
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
    public String getXacNhanMatKhau() {
        return xacNhanMatKhau;
    }
    
    public void setXacNhanMatKhau(String xacNhanMatKhau) {
        this.xacNhanMatKhau = xacNhanMatKhau;
    }
    
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    
    public String getGioiTinh() {
        return gioiTinh;
    }
    
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    
    public Boolean getDongYDieuKhoan() {
        return dongYDieuKhoan;
    }
    
    public void setDongYDieuKhoan(Boolean dongYDieuKhoan) {
        this.dongYDieuKhoan = dongYDieuKhoan;
    }
}
