package com.example.Test.service;

import com.example.Test.dto.OrganizationCreateRequest;
import com.example.Test.entity.Organization;
import com.example.Test.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Organization Service Tests")
class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;

    private OrganizationCreateRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new OrganizationCreateRequest();
        validRequest.setOrgName("Valid Organization");
        validRequest.setAddress("123 Main St");
        validRequest.setPhone("123456789");
        validRequest.setEmail("test@example.com");
    }

    // ========== NHÓM A: KIỂM THỬ ORGNAME ==========

    @Test
    @DisplayName("TC03: OrgName hợp lệ 3 ký tự - Lưu thành công")
    void testOrgName_Valid_3Characters() {
        // Arrange
        validRequest.setOrgName("ABC");
        when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setOrgId(1);
            return org;
        });

        // Act
        Organization result = organizationService.createOrganization(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals("ABC", result.getOrgName());
        verify(organizationRepository).save(any(Organization.class));
    }

    @Test
    @DisplayName("TC04: OrgName hợp lệ 255 ký tự - Lưu thành công")
    void testOrgName_Valid_255Characters() {
        // Arrange
        String longName = "A".repeat(255);
        validRequest.setOrgName(longName);
        when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setOrgId(1);
            return org;
        });

        // Act
        Organization result = organizationService.createOrganization(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals(longName, result.getOrgName());
        verify(organizationRepository).save(any(Organization.class));
    }

    @Test
    @DisplayName("TC06: OrgName trùng (case-insensitive) - Báo lỗi 'Organization Name already exists'")
    void testOrgName_Duplicate_CaseInsensitive() {
        // Arrange
        validRequest.setOrgName("ABC Company");
        when(organizationRepository.existsByOrgNameIgnoreCase("ABC Company")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> organizationService.createOrganization(validRequest)
        );
        
        assertEquals("Organization Name already exists", exception.getMessage());
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    // ========== NHÓM B: KIỂM THỬ PHONE ==========

    @Test
    @DisplayName("TC07: Phone rỗng - Hợp lệ")
    void testPhone_Empty_Valid() {
        // Arrange
        validRequest.setPhone("");
        when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setOrgId(1);
            return org;
        });

        // Act
        Organization result = organizationService.createOrganization(validRequest);

        // Assert
        assertNotNull(result);
        assertNull(result.getPhone());
        verify(organizationRepository).save(any(Organization.class));
    }

    @Test
    @DisplayName("TC10: Phone 9 ký tự - Hợp lệ")
    void testPhone_Valid_9Characters() {
        // Arrange
        validRequest.setPhone("123456789");
        when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setOrgId(1);
            return org;
        });

        // Act
        Organization result = organizationService.createOrganization(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals("123456789", result.getPhone());
        verify(organizationRepository).save(any(Organization.class));
    }

    @Test
    @DisplayName("TC11: Phone 12 ký tự - Hợp lệ")
    void testPhone_Valid_12Characters() {
        // Arrange
        validRequest.setPhone("123456789012");
        when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setOrgId(1);
            return org;
        });

        // Act
        Organization result = organizationService.createOrganization(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals("123456789012", result.getPhone());
        verify(organizationRepository).save(any(Organization.class));
    }

    // ========== NHÓM C: KIỂM THỬ EMAIL ==========

    @Test
    @DisplayName("TC13: Email rỗng - Hợp lệ")
    void testEmail_Empty_Valid() {
        // Arrange
        validRequest.setEmail("");
        when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setOrgId(1);
            return org;
        });

        // Act
        Organization result = organizationService.createOrganization(validRequest);

        // Assert
        assertNotNull(result);
        assertNull(result.getEmail());
        verify(organizationRepository).save(any(Organization.class));
    }

    @Test
    @DisplayName("TC15: Email đúng định dạng - Hợp lệ")
    void testEmail_Valid_Format() {
        // Arrange
        validRequest.setEmail("a@b.com");
        when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setOrgId(1);
            return org;
        });

        // Act
        Organization result = organizationService.createOrganization(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals("a@b.com", result.getEmail());
        verify(organizationRepository).save(any(Organization.class));
    }

    // ========== NHÓM E: KIỂM THỬ BỔ SUNG ==========

    @Test
    @DisplayName("TC20: Trim whitespace trong OrgName")
    void testOrgName_TrimWhitespace() {
        // Arrange
        validRequest.setOrgName("  ABC Company  ");
        when(organizationRepository.existsByOrgNameIgnoreCase("ABC Company")).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setOrgId(1);
            return org;
        });

        // Act
        Organization result = organizationService.createOrganization(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals("ABC Company", result.getOrgName());
        verify(organizationRepository).save(any(Organization.class));
    }

    @Test
    @DisplayName("TC21: Kiểm tra tồn tại OrgName")
    void testIsOrgNameExists() {
        // Arrange
        when(organizationRepository.existsByOrgNameIgnoreCase("Existing Org")).thenReturn(true);
        when(organizationRepository.existsByOrgNameIgnoreCase("New Org")).thenReturn(false);

        // Act & Assert
        assertTrue(organizationService.isOrgNameExists("Existing Org"));
        assertFalse(organizationService.isOrgNameExists("New Org"));
    }

    @Test
    @DisplayName("TC22: Lưu với optional fields null")
    void testSave_WithNullOptionalFields() {
        // Arrange
        validRequest.setAddress(null);
        validRequest.setPhone(null);
        validRequest.setEmail(null);
        when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setOrgId(1);
            return org;
        });

        // Act
        Organization result = organizationService.createOrganization(validRequest);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getOrgName());
        assertNull(result.getAddress());
        assertNull(result.getPhone());
        assertNull(result.getEmail());
        verify(organizationRepository).save(any(Organization.class));
    }

    @Test
    @DisplayName("TC23: Trim các trường optional")
    void testOptionalFields_TrimWhitespace() {
        // Arrange
        validRequest.setAddress("  123 Main St  ");
        validRequest.setPhone("  123456789  ");
        validRequest.setEmail("  test@example.com  ");
        when(organizationRepository.existsByOrgNameIgnoreCase(anyString())).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenAnswer(invocation -> {
            Organization org = invocation.getArgument(0);
            org.setOrgId(1);
            return org;
        });

        // Act
        Organization result = organizationService.createOrganization(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals("123 Main St", result.getAddress());
        assertEquals("123456789", result.getPhone());
        assertEquals("test@example.com", result.getEmail());
        verify(organizationRepository).save(any(Organization.class));
    }
}
