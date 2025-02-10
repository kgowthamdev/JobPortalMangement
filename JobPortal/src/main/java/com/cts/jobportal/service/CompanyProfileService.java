package com.cts.jobportal.service;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cts.jobportal.constants.ExceptionMessages;
import com.cts.jobportal.dto.CompanyProfileDTO;
import com.cts.jobportal.dto.CompanyProfilesResponseDTO;
import com.cts.jobportal.entity.CompanyProfile;
import com.cts.jobportal.exception.UserException;
import com.cts.jobportal.repo.CompanyProfileRepository;
import com.cts.jobportal.repo.CompanyUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyProfileService {

    @Autowired
    private CompanyProfileRepository companyProfileRepository;
    
    @Autowired
    private CompanyUserRepository companyUserRepository;
    
    private static final Logger log = LoggerFactory.getLogger(CompanyProfileService.class);

    // Create a new company profile
    @Transactional
    public CompanyProfileDTO createProfile(Long companyId, CompanyProfileDTO companyProfileDTO) {
        log.info("Creating company profile for companyId: {}", companyId);
        
        boolean exists = companyProfileRepository.findByCompanyId(companyId)
                .stream()
                .anyMatch(profile -> profile.getCompanyId().equals(companyId));
        
        if (exists) {
            log.warn("Profile already exists for companyId: {}", companyId);
            throw new UserException(ExceptionMessages.PROFILE_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }


        
        CompanyProfile newProfile = new CompanyProfile();
        newProfile.setCompanyId(companyId);
        newProfile.setCompanyName(companyProfileDTO.getCompanyName());
        newProfile.setDomain(companyProfileDTO.getDomain());
        newProfile.setDescription(companyProfileDTO.getDescription());
        newProfile.setLocation(companyProfileDTO.getLocation());
        newProfile.setContact(companyProfileDTO.getContact());

        CompanyProfile savedProfile = companyProfileRepository.save(newProfile);
        log.info("Company profile created for companyId: {}", companyId);
        return mapToDTO(savedProfile);
    }

    // Get all profiles for a specific company
    public CompanyProfilesResponseDTO getAllProfiles(Long companyId) {
    	
    	// Check if the company exists in the database
        boolean applicantExists = companyUserRepository.existsById(companyId);
        if (!applicantExists) {
            throw new UserException(ExceptionMessages.COMPANY_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        
        List<CompanyProfile> profiles = companyProfileRepository.findByCompanyId(companyId);
        List<CompanyProfileDTO> profileDTOs = profiles.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new CompanyProfilesResponseDTO(profileDTOs.size(), profileDTOs);
    }

    // Get a specific profile by ID
    public CompanyProfileDTO getProfileById(Long companyId, Long profileId) {
    	
    	// Check if the company exists in the database
        boolean applicantExists = companyUserRepository.existsById(companyId);
        if (!applicantExists) {
            throw new UserException(ExceptionMessages.COMPANY_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        
        CompanyProfile profile = companyProfileRepository.findByCompanyIdAndId(companyId, profileId)
                .orElseThrow(() -> new UserException(ExceptionMessages.PROFILE_NOT_FOUND,HttpStatus.NOT_FOUND));
        return mapToDTO(profile);
    }

    // Update a specific profile by ID
    @Transactional
    public CompanyProfileDTO updateProfile(Long companyId, Long profileId, CompanyProfileDTO companyProfileDTO) {
        CompanyProfile existingProfile = companyProfileRepository.findByCompanyIdAndId(companyId, profileId)
                .orElseThrow(() -> new UserException(ExceptionMessages.PROFILE_NOT_FOUND,HttpStatus.NOT_FOUND));
        
        if (!existingProfile.getContact().equals(companyProfileDTO.getContact()) && companyProfileRepository.existsByContact(companyProfileDTO.getContact())) {
            throw new UserException(ExceptionMessages.CONATCT_NO_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        
        existingProfile.setCompanyName(companyProfileDTO.getCompanyName());
        existingProfile.setDomain(companyProfileDTO.getDomain());
        existingProfile.setDescription(companyProfileDTO.getDescription());
        existingProfile.setLocation(companyProfileDTO.getLocation());
        existingProfile.setContact(companyProfileDTO.getContact());

        CompanyProfile updatedProfile = companyProfileRepository.save(existingProfile);
        log.info("Company profile updated for companyId: {}", companyId);
        return mapToDTO(updatedProfile);
    }

    // Delete a specific profile by ID
    @Transactional
    public void deleteProfile(Long companyId, Long profileId) {
        CompanyProfile profile = companyProfileRepository.findByCompanyIdAndId(companyId, profileId)
                .orElseThrow(() -> new UserException(ExceptionMessages.PROFILE_NOT_FOUND,HttpStatus.NOT_FOUND));

        companyProfileRepository.delete(profile);
        log.info("Company profile deleted for companyId: {}", companyId);
    }

    // Helper method to map CompanyProfile to DTO
    private CompanyProfileDTO mapToDTO(CompanyProfile companyProfile) {
        CompanyProfileDTO dto = new CompanyProfileDTO();
        dto.setId(companyProfile.getId());
        dto.setCompanyId(companyProfile.getCompanyId());
        dto.setCompanyName(companyProfile.getCompanyName());
        dto.setDomain(companyProfile.getDomain());
        dto.setDescription(companyProfile.getDescription());
        dto.setLocation(companyProfile.getLocation());
        dto.setContact(companyProfile.getContact());
        return dto;
    }
}
