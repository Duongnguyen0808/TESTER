package com.example.bai5.controller;

import com.example.bai5.dto.CustomerRegistrationDTO;
import com.example.bai5.model.Customer;
import com.example.bai5.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new CustomerRegistrationDTO());
        return "registration";
    }
    
    @PostMapping("/register")
    public String registerCustomer(@Valid @ModelAttribute("customer") CustomerRegistrationDTO dto,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        
        // Kiểm tra lỗi validation cơ bản
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        
        // Kiểm tra các validation tùy chỉnh
        Map<String, String> customErrors = customerService.validateCustomer(dto);
        if (!customErrors.isEmpty()) {
            customErrors.forEach((field, message) -> {
                model.addAttribute(field + "Error", message);
            });
            return "registration";
        }
        
        try {
            // Đăng ký khách hàng
            Customer savedCustomer = customerService.registerCustomer(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký tài khoản thành công!");
            return "redirect:/success";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            return "registration";
        }
    }
    
    @GetMapping("/success")
    public String showSuccess() {
        return "success";
    }
}
