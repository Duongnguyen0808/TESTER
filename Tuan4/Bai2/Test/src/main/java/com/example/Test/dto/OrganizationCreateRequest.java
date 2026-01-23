package com.example.Test.dto;

import jakarta.validation.constraints.*;

public class OrganizationCreateRequest {

    @NotBlank(message = "Organization Name không được để trống")
    @Size(min = 3, max = 255, message = "Organization Name phải có từ 3 đến 255 ký tự")
    private String orgName;

    @Size(max = 255, message = "Address không được vượt quá 255 ký tự")
    private String address;

    @Pattern(regexp = "^$|^[0-9]{9,12}$", message = "Phone chỉ chứa số và phải có từ 9 đến 12 ký tự")
    private String phone;

    @Email(message = "Email không đúng định dạng")
    private String email;

    // Constructors
    public OrganizationCreateRequest() {
    }

    public OrganizationCreateRequest(String orgName, String address, String phone, String email) {
        this.orgName = orgName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
