package com.cts.jobportaltest.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.jobportal.dto.UserProfileDTO;
import com.cts.jobportal.dto.UserProfilesResponseDTO;
import com.cts.jobportal.entity.UserProfile;
import com.cts.jobportal.repo.UserProfileRepository;
import com.cts.jobportal.service.UserProfileService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserProfileServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProfile() {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setFirstname("John");
        dto.setLastname("Doe");
        dto.setEmail("john.doe@example.com");
        dto.setContact("1234567890");
        dto.setLocation("New York");
        dto.setDob("1990-01-01");
        dto.setDescription("Software Developer");
        dto.setSkills(Arrays.asList("Java", "Spring Boot"));
        dto.setResume("resume.pdf");

        UserProfile userProfile = new UserProfile();
        userProfile.setFirstname("John");
        userProfile.setLastname("Doe");
        userProfile.setEmail("john.doe@example.com");
        userProfile.setContact("1234567890");
        userProfile.setLocation("New York");
        userProfile.setDob("1990-01-01");
        userProfile.setDescription("Software Developer");
        userProfile.setSkills(Arrays.asList("Java", "Spring Boot"));
        userProfile.setResume("resume.pdf");

        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfileDTO result = userProfileService.createProfile(1L, dto);

        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("1234567890", result.getContact());
        assertEquals("New York", result.getLocation());
        assertEquals("1990-01-01", result.getDob());
        assertEquals("Software Developer", result.getDescription());
        assertEquals(Arrays.asList("Java", "Spring Boot"), result.getSkills());
        assertEquals("resume.pdf", result.getResume());
    }

    @Test
    public void testGetAllProfiles() {
        UserProfile profile1 = new UserProfile();
        profile1.setFirstname("John");
        profile1.setLastname("Doe");
        profile1.setEmail("john.doe@example.com");
        profile1.setContact("1234567890");
        profile1.setLocation("New York");
        profile1.setDob("1990-01-01");
        profile1.setDescription("Software Developer");
        profile1.setSkills(Arrays.asList("Java", "Spring Boot"));
        profile1.setResume("resume.pdf");

        UserProfile profile2 = new UserProfile();
        profile2.setFirstname("Jane");
        profile2.setLastname("Doe");
        profile2.setEmail("jane.doe@example.com");
        profile2.setContact("0987654321");
        profile2.setLocation("San Francisco");
        profile2.setDob("1992-02-02");
        profile2.setDescription("Data Scientist");
        profile2.setSkills(Arrays.asList("Python", "Machine Learning"));
        profile2.setResume("resume2.pdf");

        List<UserProfile> profiles = Arrays.asList(profile1, profile2);

        when(userProfileRepository.findByApplicantId(anyLong())).thenReturn(profiles);

        UserProfilesResponseDTO result = userProfileService.getAllProfiles(1L);

        assertNotNull(result);
        assertEquals(2, result.getCount());
        assertEquals(2, result.getProfiles().size());
    }

    @Test
    public void testGetProfileById() {
        UserProfile profile = new UserProfile();
        profile.setFirstname("John");
        profile.setLastname("Doe");
        profile.setEmail("john.doe@example.com");
        profile.setContact("1234567890");
        profile.setLocation("New York");
        profile.setDob("1990-01-01");
        profile.setDescription("Software Developer");
        profile.setSkills(Arrays.asList("Java", "Spring Boot"));
        profile.setResume("resume.pdf");

        when(userProfileRepository.findByIdAndApplicantId(anyLong(), anyLong())).thenReturn(Optional.of(profile));

        UserProfileDTO result = userProfileService.getProfileById(1L, 1L);

        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("1234567890", result.getContact());
        assertEquals("New York", result.getLocation());
        assertEquals("1990-01-01", result.getDob());
        assertEquals("Software Developer", result.getDescription());
        assertEquals(Arrays.asList("Java", "Spring Boot"), result.getSkills());
        assertEquals("resume.pdf", result.getResume());
    }

    @Test
    public void testUpdateProfile() {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setFirstname("John Updated");
        dto.setLastname("Doe Updated");
        dto.setEmail("john.updated@example.com");
        dto.setContact("0987654321");
        dto.setLocation("Los Angeles");
        dto.setDob("1991-01-01");
        dto.setDescription("Senior Software Developer");
        dto.setSkills(Arrays.asList("Java", "Spring Boot", "Microservices"));
        dto.setResume("updated_resume.pdf");

        UserProfile existingProfile = new UserProfile();
        existingProfile.setFirstname("John");
        existingProfile.setLastname("Doe");
        existingProfile.setEmail("john.doe@example.com");
        existingProfile.setContact("1234567890");
        existingProfile.setLocation("New York");
        existingProfile.setDob("1990-01-01");
        existingProfile.setDescription("Software Developer");
        existingProfile.setSkills(Arrays.asList("Java", "Spring Boot"));
        existingProfile.setResume("resume.pdf");

        when(userProfileRepository.findByIdAndApplicantId(anyLong(), anyLong())).thenReturn(Optional.of(existingProfile));
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(existingProfile);

        UserProfileDTO result = userProfileService.updateProfile(1L, 1L, dto);

        assertNotNull(result);
        assertEquals("John Updated", result.getFirstname());
        assertEquals("Doe Updated", result.getLastname());
        assertEquals("john.updated@example.com", result.getEmail());
        assertEquals("0987654321", result.getContact());
        assertEquals("Los Angeles", result.getLocation());
        assertEquals("1991-01-01", result.getDob());
        assertEquals("Senior Software Developer", result.getDescription());
        assertEquals(Arrays.asList("Java", "Spring Boot", "Microservices"), result.getSkills());
        assertEquals("updated_resume.pdf", result.getResume());
    }

    @Test
    public void testDeleteProfile() {
        UserProfile profile = new UserProfile();
        profile.setFirstname("John");
        profile.setLastname("Doe");
        profile.setEmail("john.doe@example.com");
        profile.setContact("1234567890");
        profile.setLocation("New York");
        profile.setDob("1990-01-01");
        profile.setDescription("Software Developer");
        profile.setSkills(Arrays.asList("Java", "Spring Boot"));
        profile.setResume("resume.pdf");

        when(userProfileRepository.findByIdAndApplicantId(anyLong(), anyLong())).thenReturn(Optional.of(profile));
        doNothing().when(userProfileRepository).delete(any(UserProfile.class));

        userProfileService.deleteProfile(1L, 1L);

        verify(userProfileRepository, times(1)).delete(profile);
    }
}