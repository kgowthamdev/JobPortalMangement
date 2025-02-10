package com.cts.jobportal.controller;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.jobportal.dto.CompanyProfileDTO;
import com.cts.jobportal.dto.CompanyProfilesResponseDTO;
import com.cts.jobportal.service.CompanyProfileService;


@RestController
@RequestMapping("/api/v1/company/{company_id}/profile") 
public class CompanyProfileController {

    private static final Logger log = LoggerFactory.getLogger(CompanyProfileController.class);

    @Autowired
    private CompanyProfileService companyProfileService;
    
    // Create a new company profile
    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping
    public ResponseEntity<CompanyProfileDTO> createProfile(@PathVariable Long company_id, @Valid @RequestBody CompanyProfileDTO companyProfileDTO) {
        log.info("Creating profile for company_id: {}", company_id);
    	//SecurityUtils.checkUserAuthorization(company_id);
    	CompanyProfileDTO createdProfile = companyProfileService.createProfile(company_id, companyProfileDTO);
        log.info("Profile created successfully for company_id: {}", company_id);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    // Get all profiles for a specific company
    @GetMapping
    public ResponseEntity<CompanyProfilesResponseDTO> getAllProfiles(@PathVariable Long company_id) {
        log.info("Fetching all profiles for company_id: {}", company_id);
        CompanyProfilesResponseDTO response = companyProfileService.getAllProfiles(company_id);
        log.info("Fetched {} profiles for company_id: {}", response.getCompanyProfiles().size(), company_id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a specific profile by ID
    @GetMapping("/{profile_id}")
    public ResponseEntity<CompanyProfileDTO> getProfileById(@PathVariable Long company_id, @PathVariable Long profile_id) {
        log.info("Fetching profile with profile_id: {} for company_id: {}", profile_id, company_id);
        CompanyProfileDTO profileDTO = companyProfileService.getProfileById(company_id, profile_id);
        log.info("Fetched profile with profile_id: {} for company_id: {}", profile_id, company_id);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }
    
    // Update a specific profile by ID
    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{profile_id}")
    public ResponseEntity<CompanyProfileDTO> updateProfile(@PathVariable Long company_id, @PathVariable Long profile_id, @RequestBody CompanyProfileDTO companyProfileDTO) {
        log.info("Updating profile with profile_id: {} for company_id: {}", profile_id, company_id);
    	//S/ecurityUtils.checkUserAuthorization(company_id);
    	CompanyProfileDTO updatedProfile = companyProfileService.updateProfile(company_id, profile_id, companyProfileDTO);
        log.info("Updated profile with profile_id: {} for company_id: {}", profile_id, company_id);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // Delete a specific profile by ID
    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/{profile_id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long company_id, @PathVariable Long profile_id) {
        log.info("Deleting profile with profile_id: {} for company_id: {}", profile_id, company_id);
    	//SecurityUtils.checkUserAuthorization(company_id);
    	companyProfileService.deleteProfile(company_id, profile_id);
        log.info("Deleted profile with profile_id: {} for company_id: {}", profile_id, company_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
