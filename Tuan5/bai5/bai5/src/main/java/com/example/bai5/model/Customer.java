package com.example.bai5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "customers")
public class Customer {
    
    @Id
    @Column(name = "ma_khach_hang", unique = true, nullable = false, length = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$", message = "Mã khách hàng phải từ 6-10 ký tự, chỉ chứa chữ cái và số")
    @NotBlank(message = "Mã khách hàng không được để trống")
    private String maKhachHang;
    
    @Column(name = "ho_ten", nullable = false, length = 50)
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 5, max = 50, message = "Họ và tên phải từ 5-50 ký tự")
    private String hoTen;
    
    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;
    
    @Column(name = "so_dien_thoai", nullable = false, length = 12)
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0[0-9]{9,11}$", message = "Số điện thoại phải từ 10-12 số và bắt đầu bằng số 0")
    private String soDienThoai;
    
    @Column(name = "dia_chi", nullable = false, length = 255)
    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String diaChi;
    
    @Column(name = "mat_khau", nullable = false)
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String matKhau;
    
    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;
    
    @Column(name = "gioi_tinh", length = 10)
    private String gioiTinh;
    
    @Column(name = "dong_y_dieu_khoan", nullable = false)
    private Boolean dongYDieuKhoan = false;
    
    // Constructors
    public Customer() {
    }
    
    public Customer(String maKhachHang, String hoTen, String email, String soDienThoai, 
                   String diaChi, String matKhau) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.matKhau = matKhau;
    }
    
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
