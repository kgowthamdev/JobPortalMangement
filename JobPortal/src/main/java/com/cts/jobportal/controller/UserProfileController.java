package com.cts.jobportal.controller;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.jobportal.dto.UserProfileDTO;
import com.cts.jobportal.dto.UserProfilesResponseDTO;
import com.cts.jobportal.service.UserProfileService;

@RestController
@RequestMapping("/api/v1/applicants/{application_id}/profiles")
public class UserProfileController {

    private static final Logger log = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private UserProfileService userProfileService;
    
    // Create a new user profile
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<UserProfileDTO> createProfile(@PathVariable Long application_id, @Valid @RequestBody UserProfileDTO userProfileDTO) {
        log.info("Creating user profile for application_id: {}", application_id);
        UserProfileDTO createdProfile = userProfileService.createProfile(application_id, userProfileDTO);
        log.info("User profile created for application_id: {}", application_id);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    // Get all user profiles for a specific applicant
    @GetMapping
    public ResponseEntity<UserProfilesResponseDTO> getAllProfiles(@PathVariable Long application_id) {
        log.info("Fetching all user profiles for application_id: {}", application_id);
        UserProfilesResponseDTO response = userProfileService.getAllProfiles(application_id);
        log.info("Fetched {} user profiles for application_id: {}", response.getProfiles().size(), application_id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a specific user profile by ID
    @GetMapping("/{profile_id}")
    public ResponseEntity<UserProfileDTO> getProfileById(@PathVariable Long application_id, @PathVariable Long profile_id) {
        log.info("Fetching user profile with profile_id: {} for application_id: {}", profile_id, application_id);
        UserProfileDTO profileDTO = userProfileService.getProfileById(application_id, profile_id);
        log.info("Fetched user profile with profile_id: {} for application_id: {}", profile_id, application_id);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserProfileDTO> getUserProfileById(@PathVariable Long application_id, @PathVariable Long id) {
        log.info("Fetching user profile with d: {}", id);
        UserProfileDTO profileDTO = userProfileService.getUserProfileById(id);
        log.info("Fetched user profile with id: {}", id);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }

    // Update a specific user profile by ID
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{profile_id}")
    public ResponseEntity<UserProfileDTO> updateProfile(@PathVariable Long application_id, @PathVariable Long profile_id, @RequestBody UserProfileDTO userProfileDTO) {
        log.info("Updating user profile with profile_id: {} for application_id: {}", profile_id, application_id);
        UserProfileDTO updatedProfile = userProfileService.updateProfile(application_id, profile_id, userProfileDTO);
        log.info("Updated user profile with profile_id: {} for application_id: {}", profile_id, application_id);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // Delete a specific user profile by ID
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{profile_id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long application_id, @PathVariable Long profile_id) {
        log.info("Deleting user profile with profile_id: {} for application_id: {}", profile_id, application_id);
        userProfileService.deleteProfile(application_id, profile_id);
        log.info("Deleted user profile with profile_id: {} for application_id: {}", profile_id, application_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
