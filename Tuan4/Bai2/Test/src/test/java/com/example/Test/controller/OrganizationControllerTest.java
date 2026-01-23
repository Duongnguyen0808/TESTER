package com.example.Test.controller;

import com.example.Test.dto.OrganizationCreateRequest;
import com.example.Test.entity.Organization;
import com.example.Test.service.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrganizationController.class)
@DisplayName("Organization Controller Tests")
class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationService organizationService;

    private Organization mockOrganization;

    @BeforeEach
    void setUp() {
        mockOrganization = new Organization();
        mockOrganization.setOrgId(1);
        mockOrganization.setOrgName("Test Organization");
        mockOrganization.setAddress("123 Main St");
        mockOrganization.setPhone("123456789");
        mockOrganization.setEmail("test@example.com");
    }

    // ========== NHÓM A: KIỂM THỬ VALIDATION ORGNAME ==========

    @Test
    @DisplayName("TC01: OrgName rỗng - Lỗi validation")
    void testOrgName_Empty_ValidationError() throws Exception {
        mockMvc.perform(post("/organizations")
                .param("orgName", "")
                .param("address", "123 Main St")
                .param("phone", "123456789")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization-form"))
                .andExpect(model().attributeHasFieldErrors("organizationRequest", "orgName"));

        verify(organizationService, never()).createOrganization(any());
    }

    @Test
    @DisplayName("TC02: OrgName 2 ký tự - Lỗi min length")
    void testOrgName_2Characters_ValidationError() throws Exception {
        mockMvc.perform(post("/organizations")
                .param("orgName", "AB")
                .param("address", "123 Main St")
                .param("phone", "123456789")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization-form"))
                .andExpect(model().attributeHasFieldErrors("organizationRequest", "orgName"));

        verify(organizationService, never()).createOrganization(any());
    }

    @Test
    @DisplayName("TC05: OrgName 256 ký tự - Lỗi max length")
    void testOrgName_256Characters_ValidationError() throws Exception {
        String longName = "A".repeat(256);
        
        mockMvc.perform(post("/organizations")
                .param("orgName", longName)
                .param("address", "123 Main St")
                .param("phone", "123456789")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization-form"))
                .andExpect(model().attributeHasFieldErrors("organizationRequest", "orgName"));

        verify(organizationService, never()).createOrganization(any());
    }

    // ========== NHÓM B: KIỂM THỬ VALIDATION PHONE ==========

    @Test
    @DisplayName("TC08: Phone có chữ - Lỗi validation")
    void testPhone_WithLetters_ValidationError() throws Exception {
        mockMvc.perform(post("/organizations")
                .param("orgName", "Valid Organization")
                .param("address", "123 Main St")
                .param("phone", "09A1234567")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization-form"))
                .andExpect(model().attributeHasFieldErrors("organizationRequest", "phone"));

        verify(organizationService, never()).createOrganization(any());
    }

    @Test
    @DisplayName("TC09: Phone 8 ký tự - Lỗi boundary")
    void testPhone_8Characters_ValidationError() throws Exception {
        mockMvc.perform(post("/organizations")
                .param("orgName", "Valid Organization")
                .param("address", "123 Main St")
                .param("phone", "12345678")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization-form"))
                .andExpect(model().attributeHasFieldErrors("organizationRequest", "phone"));

        verify(organizationService, never()).createOrganization(any());
    }

    @Test
    @DisplayName("TC12: Phone 13 ký tự - Lỗi boundary")
    void testPhone_13Characters_ValidationError() throws Exception {
        mockMvc.perform(post("/organizations")
                .param("orgName", "Valid Organization")
                .param("address", "123 Main St")
                .param("phone", "1234567890123")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization-form"))
                .andExpect(model().attributeHasFieldErrors("organizationRequest", "phone"));

        verify(organizationService, never()).createOrganization(any());
    }

    // ========== NHÓM C: KIỂM THỬ VALIDATION EMAIL ==========

    @Test
    @DisplayName("TC14: Email sai định dạng - Lỗi validation")
    void testEmail_InvalidFormat_ValidationError() throws Exception {
        mockMvc.perform(post("/organizations")
                .param("orgName", "Valid Organization")
                .param("address", "123 Main St")
                .param("phone", "123456789")
                .param("email", "abc@"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization-form"))
                .andExpect(model().attributeHasFieldErrors("organizationRequest", "email"));

        verify(organizationService, never()).createOrganization(any());
    }

    // ========== NHÓM D: KIỂM THỬ LUỒNG NGHIỆP VỤ ==========

    @Test
    @DisplayName("TC16: POST save hợp lệ - Hiển thị success message + Director enabled")
    void testSave_Valid_ShowSuccessAndDirector() throws Exception {
        when(organizationService.createOrganization(any(OrganizationCreateRequest.class)))
                .thenReturn(mockOrganization);

        mockMvc.perform(post("/organizations")
                .param("orgName", "Valid Organization")
                .param("address", "123 Main St")
                .param("phone", "123456789")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization-success"))
                .andExpect(model().attribute("successMessage", "Save successfully"))
                .andExpect(model().attribute("showDirector", true))
                .andExpect(model().attributeExists("organization"));

        verify(organizationService, times(1)).createOrganization(any());
    }

    @Test
    @DisplayName("TC17: POST save không hợp lệ - Trả về form với lỗi")
    void testSave_Invalid_ReturnFormWithErrors() throws Exception {
        mockMvc.perform(post("/organizations")
                .param("orgName", "AB")  // Too short
                .param("phone", "abc"))   // Invalid format
                .andExpect(status().isOk())
                .andExpect(view().name("organization-form"))
                .andExpect(model().attributeHasFieldErrors("organizationRequest", "orgName", "phone"));

        verify(organizationService, never()).createOrganization(any());
    }

    @Test
    @DisplayName("TC18: GET /organizations/new - Hiển thị form tạo mới")
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/organizations/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization-form"))
                .andExpect(model().attributeExists("organizationRequest"));
    }

    @Test
    @DisplayName("TC19: GET /organizations/{id}/directors - Hiển thị Director page")
    void testShowDirectorManagement() throws Exception {
        mockMvc.perform(get("/organizations/1/directors"))
                .andExpect(status().isOk())
                .andExpect(view().name("director-management"))
                .andExpect(model().attribute("orgId", 1));
    }

    @Test
    @DisplayName("Test duplicate OrgName - Show error message")
    void testSave_DuplicateOrgName_ShowError() throws Exception {
        when(organizationService.createOrganization(any(OrganizationCreateRequest.class)))
                .thenThrow(new IllegalArgumentException("Organization Name already exists"));

        mockMvc.perform(post("/organizations")
                .param("orgName", "Existing Organization")
                .param("address", "123 Main St")
                .param("phone", "123456789")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("organization-form"))
                .andExpect(model().attribute("errorMessage", "Organization Name already exists"));

        verify(organizationService, times(1)).createOrganization(any());
    }
}
