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

import com.cts.jobportal.constants.JobApplicationStatus;
import com.cts.jobportal.dto.JobApplicationDTO;
import com.cts.jobportal.entity.JobApplication;
import com.cts.jobportal.repo.JobApplicationRepository;
import com.cts.jobportal.service.JobApplicationService;

import java.util.Arrays;
import java.util.List;

public class JobApplicationServiceTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @InjectMocks
    private JobApplicationService jobApplicationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApplyForJob() {
        JobApplicationDTO request = new JobApplicationDTO();
        request.setJobId(1L);
        request.setApplicantId(1L);
        request.setStatus(JobApplicationStatus.PENDING);

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobId(1L);
        jobApplication.setApplicantId(1L);
        jobApplication.setStatus(JobApplicationStatus.PENDING);

        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(jobApplication);

        JobApplicationDTO result = jobApplicationService.applyForJob(request);

        assertNotNull(result);
        assertEquals(1L, result.getJobId());
        assertEquals(1L, result.getApplicantId());
        assertEquals(JobApplicationStatus.PENDING, result.getStatus());
    }

    @Test
    public void testGetAllApplicationsByApplicant() {
        JobApplication jobApplication1 = new JobApplication();
        jobApplication1.setJobId(1L);
        jobApplication1.setApplicantId(1L);
        jobApplication1.setStatus(JobApplicationStatus.ACCEPTED);

        JobApplication jobApplication2 = new JobApplication();
        jobApplication2.setJobId(2L);
        jobApplication2.setApplicantId(1L);
        jobApplication2.setStatus(JobApplicationStatus.REJECTED);

        List<JobApplication> applications = Arrays.asList(jobApplication1, jobApplication2);

        when(jobApplicationRepository.findByApplicantId(anyLong())).thenReturn(applications);

        List<JobApplicationDTO> result = jobApplicationService.getAllApplicationsByApplicant(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(JobApplicationStatus.ACCEPTED, result.get(0).getStatus());
        assertEquals(JobApplicationStatus.REJECTED, result.get(1).getStatus());
    }
}