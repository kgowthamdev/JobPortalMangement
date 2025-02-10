package com.cts.jobportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.jobportal.dto.JobApplicationDTO;
import com.cts.jobportal.service.JobApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applicants/{applicantId}/applications")
public class JobApplicationController {

    private static final Logger log = LoggerFactory.getLogger(JobApplicationController.class);

    @Autowired
    private JobApplicationService jobApplicationService;

    // Apply for a Job
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<JobApplicationDTO> applyForJob(@PathVariable Long applicantId, @RequestBody JobApplicationDTO request) {
        log.info("Applying for job with applicantId: {}", applicantId);
        request.setApplicantId(applicantId);
        JobApplicationDTO jobApplication = jobApplicationService.applyForJob(request);
        log.info("Job application created for applicantId: {}", applicantId);
        return new ResponseEntity<>(jobApplication, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<JobApplicationDTO> jobApplicationStatusUpdate(@PathVariable Long applicantId, @RequestBody JobApplicationDTO request) {
        log.info("Applying for job with applicantId: {}", applicantId);
        request.setApplicantId(applicantId);
        JobApplicationDTO jobApplication = jobApplicationService.updateJobApplicationStatus(request);
        log.info("Job application created for applicantId: {}", applicantId);
        return new ResponseEntity<>(jobApplication, HttpStatus.OK);
    }

    // Get All Applications by Applicant
    @GetMapping
    public ResponseEntity<List<JobApplicationDTO>> getAllApplicationsByApplicant(@PathVariable Long applicantId) {
        log.info("Fetching all job applications for applicantId: {}", applicantId);
        List<JobApplicationDTO> applications = jobApplicationService.getAllApplicationsByApplicant(applicantId);
        log.info("Fetched {} applications for applicantId: {}", applications.size(), applicantId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<List<JobApplicationDTO>> getAllApplicantsByJobId(@PathVariable Long jobId) {
        log.info("Fetching all job applications for jobId: {}", jobId);
        List<JobApplicationDTO> applications = jobApplicationService.getAllApplicantsByJobId(jobId);
        log.info("Fetched {} applications for jobId: {}", applications.size(), jobId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
}
