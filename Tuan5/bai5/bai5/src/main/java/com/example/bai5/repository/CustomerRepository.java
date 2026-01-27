package com.example.bai5.repository;

import com.example.bai5.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    
    Optional<Customer> findByMaKhachHang(String maKhachHang);
    
    Optional<Customer> findByEmail(String email);
    
    boolean existsByMaKhachHang(String maKhachHang);
    
    boolean existsByEmail(String email);
}
