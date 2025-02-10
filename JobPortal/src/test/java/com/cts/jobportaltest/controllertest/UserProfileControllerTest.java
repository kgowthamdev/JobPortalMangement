package com.cts.jobportaltest.controllertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.jobportal.controller.UserProfileController;
import com.cts.jobportal.dto.UserProfileDTO;
import com.cts.jobportal.dto.UserProfilesResponseDTO;
import com.cts.jobportal.service.UserProfileService;

import java.util.Arrays;

public class UserProfileControllerTest {

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private UserProfileController userProfileController;

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

        when(userProfileService.createProfile(anyLong(), any(UserProfileDTO.class))).thenReturn(dto);

        ResponseEntity<UserProfileDTO> response = userProfileController.createProfile(1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstname());
    }

    @Test
    public void testGetAllProfiles() {
        UserProfilesResponseDTO responseDTO = new UserProfilesResponseDTO(1, Arrays.asList(new UserProfileDTO()));

        when(userProfileService.getAllProfiles(anyLong())).thenReturn(responseDTO);

        ResponseEntity<UserProfilesResponseDTO> response = userProfileController.getAllProfiles(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getCount());
    }

    @Test
    public void testGetProfileById() {
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

        when(userProfileService.getProfileById(anyLong(), anyLong())).thenReturn(dto);

        ResponseEntity<UserProfileDTO> response = userProfileController.getProfileById(1L, 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstname());
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

        when(userProfileService.updateProfile(anyLong(), anyLong(), any(UserProfileDTO.class))).thenReturn(dto);

        ResponseEntity<UserProfileDTO> response = userProfileController.updateProfile(1L, 1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Updated", response.getBody().getFirstname());
    }

    @Test
    public void testDeleteProfile() {
        doNothing().when(userProfileService).deleteProfile(anyLong(), anyLong());

        ResponseEntity<Void> response = userProfileController.deleteProfile(1L, 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}