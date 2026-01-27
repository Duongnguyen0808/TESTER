package com.example.bai5.service;

import com.example.bai5.dto.CustomerRegistrationDTO;
import com.example.bai5.model.Customer;
import com.example.bai5.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public Map<String, String> validateCustomer(CustomerRegistrationDTO dto) {
        Map<String, String> errors = new HashMap<>();
        
        // Kiểm tra mã khách hàng trùng lặp
        if (customerRepository.existsByMaKhachHang(dto.getMaKhachHang())) {
            errors.put("maKhachHang", "Mã khách hàng đã tồn tại");
        }
        
        // Kiểm tra email trùng lặp
        if (customerRepository.existsByEmail(dto.getEmail())) {
            errors.put("email", "Email đã được sử dụng");
        }
        
        // Kiểm tra mật khẩu khớp
        if (!dto.getMatKhau().equals(dto.getXacNhanMatKhau())) {
            errors.put("xacNhanMatKhau", "Mật khẩu xác nhận không khớp");
        }
        
        // Kiểm tra độ tuổi (nếu có ngày sinh)
        if (dto.getNgaySinh() != null) {
            int age = Period.between(dto.getNgaySinh(), LocalDate.now()).getYears();
            if (age < 18) {
                errors.put("ngaySinh", "Bạn phải đủ 18 tuổi để đăng ký");
            }
        }
        
        return errors;
    }
    
    public Customer registerCustomer(CustomerRegistrationDTO dto) {
        Customer customer = new Customer();
        customer.setMaKhachHang(dto.getMaKhachHang());
        customer.setHoTen(dto.getHoTen());
        customer.setEmail(dto.getEmail());
        customer.setSoDienThoai(dto.getSoDienThoai());
        customer.setDiaChi(dto.getDiaChi());
        customer.setMatKhau(dto.getMatKhau()); // Trong thực tế nên mã hóa password
        customer.setNgaySinh(dto.getNgaySinh());
        customer.setGioiTinh(dto.getGioiTinh());
        customer.setDongYDieuKhoan(dto.getDongYDieuKhoan());
        
        return customerRepository.save(customer);
    }
}
