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

import com.cts.jobportal.constants.JobApplicationStatus;
import com.cts.jobportal.controller.JobApplicationController;
import com.cts.jobportal.dto.JobApplicationDTO;
import com.cts.jobportal.service.JobApplicationService;

import java.util.Arrays;
import java.util.List;

public class JobApplicationControllerTest {

    @Mock
    private JobApplicationService jobApplicationService;

    @InjectMocks
    private JobApplicationController jobApplicationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApplyForJob() {
        JobApplicationDTO dto = new JobApplicationDTO();
        dto.setJobId(1L);
        dto.setApplicantId(1L);
        dto.setStatus(JobApplicationStatus.PENDING);

        when(jobApplicationService.applyForJob(any(JobApplicationDTO.class))).thenReturn(dto);

        ResponseEntity<JobApplicationDTO> response = jobApplicationController.applyForJob(1L, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getJobId());
    }

    @Test
    public void testGetAllApplicationsByApplicant() {
        List<JobApplicationDTO> applications = Arrays.asList(new JobApplicationDTO());

        when(jobApplicationService.getAllApplicationsByApplicant(anyLong())).thenReturn(applications);

        ResponseEntity<List<JobApplicationDTO>> response = jobApplicationController.getAllApplicationsByApplicant(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}