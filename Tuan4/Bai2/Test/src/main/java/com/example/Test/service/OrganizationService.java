package com.example.Test.service;

import com.example.Test.dto.OrganizationCreateRequest;
import com.example.Test.entity.Organization;
import com.example.Test.repository.OrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * Check if an organization name already exists (case-insensitive)
     */
    public boolean isOrgNameExists(String orgName) {
        if (orgName == null || orgName.trim().isEmpty()) {
            return false;
        }
        return organizationRepository.existsByOrgNameIgnoreCase(orgName.trim());
    }

    /**
     * Create and save a new organization
     */
    @Transactional
    public Organization createOrganization(OrganizationCreateRequest request) {
        // Trim all fields
        String trimmedOrgName = request.getOrgName() != null ? request.getOrgName().trim() : null;
        String trimmedAddress = request.getAddress() != null && !request.getAddress().isEmpty() 
                ? request.getAddress().trim() : null;
        String trimmedPhone = request.getPhone() != null && !request.getPhone().isEmpty() 
                ? request.getPhone().trim() : null;
        String trimmedEmail = request.getEmail() != null && !request.getEmail().isEmpty() 
                ? request.getEmail().trim() : null;

        // Check if organization name already exists
        if (isOrgNameExists(trimmedOrgName)) {
            throw new IllegalArgumentException("Organization Name already exists");
        }

        // Create new organization
        Organization organization = new Organization();
        organization.setOrgName(trimmedOrgName);
        organization.setAddress(trimmedAddress);
        organization.setPhone(trimmedPhone);
        organization.setEmail(trimmedEmail);

        // Save to database
        return organizationRepository.save(organization);
    }
}
