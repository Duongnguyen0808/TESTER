package com.example.Test.controller;

import com.example.Test.dto.OrganizationCreateRequest;
import com.example.Test.entity.Organization;
import com.example.Test.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * GET /organizations/new - Show create form
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("organizationRequest", new OrganizationCreateRequest());
        return "organization-form";
    }

    /**
     * POST /organizations - Save organization
     */
    @PostMapping
    public String saveOrganization(
            @Valid @ModelAttribute("organizationRequest") OrganizationCreateRequest request,
            BindingResult bindingResult,
            Model model) {
        
        // If validation errors exist, return to form
        if (bindingResult.hasErrors()) {
            return "organization-form";
        }

        try {
            // Try to save organization
            Organization savedOrganization = organizationService.createOrganization(request);
            
            // Add success message and saved organization to model
            model.addAttribute("successMessage", "Save successfully");
            model.addAttribute("organization", savedOrganization);
            model.addAttribute("showDirector", true);
            
            return "organization-success";
            
        } catch (IllegalArgumentException e) {
            // Handle duplicate organization name
            model.addAttribute("errorMessage", e.getMessage());
            return "organization-form";
        }
    }

    /**
     * GET /organizations/{orgId}/directors - Show director management page
     */
    @GetMapping("/{orgId}/directors")
    public String showDirectorManagement(@PathVariable Integer orgId, Model model) {
        model.addAttribute("orgId", orgId);
        return "director-management";
    }

    /**
     * GET /organizations - Show list of organizations (optional)
     */
    @GetMapping
    public String listOrganizations() {
        // Placeholder for list view
        return "redirect:/organizations/new";
    }
}
