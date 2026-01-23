package com.example.Test.repository;

import com.example.Test.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    
    /**
     * Check if an organization with the given name exists (case-insensitive)
     */
    boolean existsByOrgNameIgnoreCase(String orgName);
}
