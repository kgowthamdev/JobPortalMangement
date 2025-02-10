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

import com.cts.jobportal.controller.JobPostController;
import com.cts.jobportal.dto.JobPostDTO;
import com.cts.jobportal.dto.JobPostsResponseDTO;
import com.cts.jobportal.service.JobPostService;

import java.util.Arrays;

public class JobPostControllerTest {

    @Mock
    private JobPostService jobPostService;

    @InjectMocks
    private JobPostController jobPostController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateJobPost() {
        JobPostDTO dto = new JobPostDTO();
        dto.setCompanyId(1L);
        dto.setJobTitle("Software Engineer");

        when(jobPostService.createJobPost(any(JobPostDTO.class))).thenReturn(dto);

        ResponseEntity<JobPostDTO> response = jobPostController.createJobPost(1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Software Engineer", response.getBody().getJobTitle());
    }

    @Test
    public void testGetAllJobPosts() {
        JobPostsResponseDTO responseDTO = new JobPostsResponseDTO(1, Arrays.asList(new JobPostDTO()));

        when(jobPostService.getAllJobPosts(anyLong())).thenReturn(responseDTO);

        ResponseEntity<JobPostsResponseDTO> response = jobPostController.getAllJobPosts(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getCount());
    }

    @Test
    public void testGetJobPostById() {
        JobPostDTO dto = new JobPostDTO();
        dto.setCompanyId(1L);
        dto.setJobTitle("Software Engineer");

        when(jobPostService.getJobPostById(anyLong(), anyLong())).thenReturn(dto);

        ResponseEntity<JobPostDTO> response = jobPostController.getJobPostById(1L, 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Software Engineer", response.getBody().getJobTitle());
    }

    @Test
    public void testUpdateJobPost() {
        JobPostDTO dto = new JobPostDTO();
        dto.setCompanyId(1L);
        dto.setJobTitle("Updated Software Engineer");
        // Set other fields...

        when(jobPostService.updateJobPost(anyLong(), anyLong(), any(JobPostDTO.class))).thenReturn(dto);

        ResponseEntity<JobPostDTO> response = jobPostController.updateJobPost(1L, 1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Software Engineer", response.getBody().getJobTitle());
    }

    @Test
    public void testDeleteJobPost() {
        doNothing().when(jobPostService).deleteJobPost(anyLong(), anyLong());

        ResponseEntity<Void> response = jobPostController.deleteJobPost(1L, 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}