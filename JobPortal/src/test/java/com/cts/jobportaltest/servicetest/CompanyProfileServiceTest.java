package com.cts.jobportaltest.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.jobportal.dto.CompanyProfileDTO;
import com.cts.jobportal.dto.CompanyProfilesResponseDTO;
import com.cts.jobportal.entity.CompanyProfile;
import com.cts.jobportal.repo.CompanyProfileRepository;
import com.cts.jobportal.service.CompanyProfileService;

public class CompanyProfileServiceTest {
	@Mock
    private CompanyProfileRepository companyProfileRepository;

    @InjectMocks
    private CompanyProfileService companyProfileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProfile() {
        CompanyProfileDTO dto = new CompanyProfileDTO();
        dto.setId(1L);
        dto.setCompanyId(1L);
        dto.setCompanyName("CTS");
        dto.setDomain("IT Industry");
        dto.setDescription("Information technology services and consulting company");
        dto.setLocation("Coimbatore");
        dto.setContact("8976493746");

        CompanyProfile profile = new CompanyProfile();
        profile.setId(1L);
        profile.setCompanyId(1L);
        profile.setCompanyName("CTS");
        profile.setDomain("IT Industry");
        profile.setDescription("Information technology services and consulting company");
        profile.setLocation("Coimbatore");
        profile.setContact("8976493746");

        when(companyProfileRepository.save(any(CompanyProfile.class))).thenReturn(profile);

        CompanyProfileDTO result = companyProfileService.createProfile(1L, dto);

        assertEquals(1L, result.getCompanyId());
        assertEquals("CTS", result.getCompanyName());
        assertEquals("IT Industry", result.getDomain());
        assertEquals("Information technology services and consulting company", result.getDescription());
        assertEquals("Coimbatore", result.getLocation());
        assertEquals("8976493746", result.getContact());
    }
    
    @Test
    public void testGetAllProfiles() {
        CompanyProfile profile1 = new CompanyProfile();
        profile1.setId(1L);
        profile1.setCompanyId(1L);
        profile1.setCompanyName("CTS");
        profile1.setDomain("IT Industry");
        profile1.setDescription("Information technology services and consulting company");
        profile1.setLocation("Coimbatore");
        profile1.setContact("8976493746");

        CompanyProfile profile2 = new CompanyProfile();
        profile2.setId(2L);
        profile2.setCompanyId(2L);
        profile2.setCompanyName("Cognizant");
        profile2.setDomain("IT Industry");
        profile2.setDescription("Information technology services and consulting company");
        profile2.setLocation("Chennai");
        profile2.setContact("9876543210");

        List<CompanyProfile> profiles = Arrays.asList(profile1, profile2);

        when(companyProfileRepository.findByCompanyId(1L)).thenReturn(profiles);

        CompanyProfilesResponseDTO result = companyProfileService.getAllProfiles(1L);

        assertEquals(2, result.getCount());
        assertEquals(2, result.getCompanyProfiles().size());
    }
    
    @Test
    public void testGetProfileById() {
        CompanyProfile profile = new CompanyProfile();
        profile.setId(1L);
        profile.setCompanyId(1L);
        profile.setCompanyName("CTS");
        profile.setDomain("IT Industry");
        profile.setDescription("Information technology services and consulting company");
        profile.setLocation("Coimbatore");
        profile.setContact("8976493746");

        when(companyProfileRepository.findByCompanyIdAndId(anyLong(), anyLong())).thenReturn(Optional.of(profile));

        CompanyProfileDTO result = companyProfileService.getProfileById(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getCompanyId());
        assertEquals("CTS", result.getCompanyName());
        assertEquals("IT Industry", result.getDomain());
        assertEquals("Information technology services and consulting company", result.getDescription());
        assertEquals("Coimbatore", result.getLocation());
        assertEquals("8976493746", result.getContact());
    }
    
    @Test
    public void testUpdateProfile() {
        CompanyProfileDTO dto = new CompanyProfileDTO();
        dto.setCompanyName("CTS Updated");
        dto.setDomain("IT Industry Updated");
        dto.setDescription("Updated description");
        dto.setLocation("Updated location");
        dto.setContact("1234567890");

        CompanyProfile existingProfile = new CompanyProfile();
        existingProfile.setCompanyId(1L);
        existingProfile.setCompanyName("CTS");
        existingProfile.setDomain("IT Industry");
        existingProfile.setDescription("Information technology services and consulting company");
        existingProfile.setLocation("Coimbatore");
        existingProfile.setContact("8976493746");

        when(companyProfileRepository.findByCompanyIdAndId(anyLong(), anyLong())).thenReturn(Optional.of(existingProfile));
        when(companyProfileRepository.save(any(CompanyProfile.class))).thenReturn(existingProfile);

        CompanyProfileDTO result = companyProfileService.updateProfile(1L, 1L, dto);

        assertEquals("CTS Updated", result.getCompanyName());
        assertEquals("IT Industry Updated", result.getDomain());
        assertEquals("Updated description", result.getDescription());
        assertEquals("Updated location", result.getLocation());
        assertEquals("8976493746", result.getContact());
    }
    
    @Test
    public void testDeleteProfile() {
        CompanyProfile profile = new CompanyProfile();
        profile.setCompanyId(1L);
        profile.setCompanyName("CTS");
        profile.setDomain("IT Industry");
        profile.setDescription("Information technology services and consulting company");
        profile.setLocation("Coimbatore");
        profile.setContact("8976493746");

        when(companyProfileRepository.findByCompanyIdAndId(anyLong(), anyLong())).thenReturn(Optional.of(profile));
        doNothing().when(companyProfileRepository).delete(any(CompanyProfile.class));

        companyProfileService.deleteProfile(1L, 1L);

        assertThrows(RuntimeException.class, () -> companyProfileService.getProfileById(1L, 1L));
    }
}
