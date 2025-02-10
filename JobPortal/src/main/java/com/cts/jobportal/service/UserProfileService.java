package com.cts.jobportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.jobportal.constants.ExceptionMessages;
import com.cts.jobportal.dto.UserProfileDTO;
import com.cts.jobportal.dto.UserProfilesResponseDTO;
import com.cts.jobportal.entity.UserProfile;
import com.cts.jobportal.exception.UserException;
import com.cts.jobportal.repo.ApplicantUserRepository;
import com.cts.jobportal.repo.UserProfileRepository;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private ApplicantUserRepository applicantUserRepository; 
    
    private static final Logger log = LoggerFactory.getLogger(UserProfileService.class);

 // Create a new user profile
    @Transactional
    public UserProfileDTO createProfile(Long applicationId, UserProfileDTO userProfileDTO) {
        log.info("Creating user profile for applicationId: {}", applicationId);
        
        boolean exists = userProfileRepository.findByApplicantId(applicationId)
                .stream()
                .anyMatch(profile -> profile.getApplicantId().equals(applicationId));
        
        if (exists) {
            log.warn("Profile already exists for applicationId: {}", applicationId);
            throw new UserException(ExceptionMessages.PROFILE_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

    	 if (userProfileRepository.existsByEmail(userProfileDTO.getEmail())) {
             throw new UserException(ExceptionMessages.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
         }
         if (userProfileRepository.existsByContact(userProfileDTO.getContact())) {
             throw new UserException(ExceptionMessages.CONATCT_NO_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
         }
         
        UserProfile userProfile = mapToEntity(userProfileDTO);
        userProfile.setApplicantId(applicationId);
        UserProfile savedProfile = userProfileRepository.save(userProfile);
        log.info("User profile created for applicationId: {}", applicationId);
        return mapToDTO(savedProfile);
    }

    // Get all user profiles for a specific applicant
    public UserProfilesResponseDTO getAllProfiles(Long applicationId) {
    	
    	// Check if the applicant exists in the database
        boolean applicantExists = applicantUserRepository.existsById(applicationId);
        if (!applicantExists) {
            throw new UserException(ExceptionMessages.APPLICANT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        
        List<UserProfile> profiles = userProfileRepository.findByApplicantId(applicationId);
        List<UserProfileDTO> profileDTOs = profiles.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new UserProfilesResponseDTO(profileDTOs.size(), profileDTOs);
    }

    // Get a specific user profile by ID
    public UserProfileDTO getProfileById(Long applicationId, Long profileId) {
    	
    	// Check if the applicant exists in the database
        boolean applicantExists = applicantUserRepository.existsById(applicationId);
        if (!applicantExists) {
            throw new UserException(ExceptionMessages.APPLICANT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        
        UserProfile profile = userProfileRepository.findByIdAndApplicantId(profileId,applicationId)
                .orElseThrow(() -> new UserException(ExceptionMessages.PROFILE_NOT_FOUND,HttpStatus.NOT_FOUND));

        return mapToDTO(profile);
    }

    public UserProfileDTO getUserProfileById(Long Id) {

        UserProfile profile = userProfileRepository.findById(Id)
                .orElseThrow(() -> new UserException(ExceptionMessages.PROFILE_NOT_FOUND,HttpStatus.NOT_FOUND));

        return mapToDTO(profile);
    }

    // Update an existing user profile
    @Transactional
    public UserProfileDTO updateProfile(Long applicationId, Long profileId, UserProfileDTO userProfileDTO) {
        UserProfile existingProfile = userProfileRepository.findByIdAndApplicantId(profileId, applicationId)
                .orElseThrow(() -> new UserException(ExceptionMessages.PROFILE_NOT_FOUND,HttpStatus.NOT_FOUND));
        
        if (!existingProfile.getEmail().equals(userProfileDTO.getEmail()) && userProfileRepository.existsByEmail(userProfileDTO.getEmail())) {
            throw new UserException(ExceptionMessages.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        if (!existingProfile.getContact().equals(userProfileDTO.getContact()) && userProfileRepository.existsByContact(userProfileDTO.getContact())) {
            throw new UserException(ExceptionMessages.CONATCT_NO_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        } 
        
        existingProfile.setFirstname(userProfileDTO.getFirstname());
        existingProfile.setLastname(userProfileDTO.getLastname());
        existingProfile.setEmail(userProfileDTO.getEmail());
        existingProfile.setContact(userProfileDTO.getContact());
        existingProfile.setLocation(userProfileDTO.getLocation());
        existingProfile.setDob(userProfileDTO.getDob());
        existingProfile.setDescription(userProfileDTO.getDescription());
        existingProfile.setSkills(userProfileDTO.getSkills());
        existingProfile.setResume(userProfileDTO.getResume());

        UserProfile updatedProfile = userProfileRepository.save(existingProfile);
        log.info("User profile updated for applicationId: {}, profileId: {}", applicationId,profileId);
        return mapToDTO(updatedProfile);
    }

    // Delete a user profile by ID
    @Transactional
    public void deleteProfile(Long applicationId, Long profileId) {
        UserProfile profile = userProfileRepository.findByIdAndApplicantId(profileId, applicationId)
                .orElseThrow(() -> new UserException("Profile not found",HttpStatus.NOT_FOUND));

        userProfileRepository.delete(profile);
        log.info("User profile deleted for applicationId: {}, profileId: {}", applicationId,profileId);
    }

    // Mapping methods
    private UserProfileDTO mapToDTO(UserProfile userProfile) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(userProfile.getId());
        dto.setApplicationId(userProfile.getApplicantId());
        dto.setFirstname(userProfile.getFirstname());
        dto.setLastname(userProfile.getLastname());
        dto.setEmail(userProfile.getEmail());
        dto.setContact(userProfile.getContact());
        dto.setLocation(userProfile.getLocation());
        dto.setDob(userProfile.getDob());
        dto.setDescription(userProfile.getDescription());
        dto.setSkills(userProfile.getSkills());
        dto.setResume(userProfile.getResume());
        return dto;
    }

    private UserProfile mapToEntity(UserProfileDTO dto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstname(dto.getFirstname());
        userProfile.setLastname(dto.getLastname());
        userProfile.setEmail(dto.getEmail());
        userProfile.setContact(dto.getContact());
        userProfile.setLocation(dto.getLocation());
        userProfile.setDob(dto.getDob());
        userProfile.setDescription(dto.getDescription());
        userProfile.setSkills(dto.getSkills());
        userProfile.setResume(dto.getResume());
        return userProfile;
    }
}
