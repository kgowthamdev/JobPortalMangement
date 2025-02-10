package com.cts.jobportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cts.jobportal.constants.ExceptionMessages;
import com.cts.jobportal.constants.JobApplicationStatus;
import com.cts.jobportal.dto.JobApplicationDTO;
import com.cts.jobportal.entity.JobApplication;
import com.cts.jobportal.exception.UserException;
import com.cts.jobportal.repo.ApplicantUserRepository;
import com.cts.jobportal.repo.JobApplicationRepository;
import com.cts.jobportal.repo.JobPostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    
    @Autowired
    private JobPostRepository jobPostRepository;
    
    @Autowired
    private ApplicantUserRepository applicantUserRepository;
    
    private static final Logger log = LoggerFactory.getLogger(JobApplicationService.class);
    
    // Apply for a Job
    public JobApplicationDTO applyForJob(JobApplicationDTO request) {
        log.info("Processing job application for jobId: {} and applicantId: {}", request.getJobId(), request.getApplicantId());
        
        boolean exists = jobApplicationRepository.findByJobIdAndApplicantId(request.getJobId(), request.getApplicantId()).isPresent();
        if (exists) {
            log.warn("Duplicate job application attempt for jobId: {} and applicantId: {}", request.getJobId(), request.getApplicantId());
            throw new UserException(ExceptionMessages.JOB_ALREADY_APPLIED, HttpStatus.CONFLICT);
        }
        
     // Check if the job_id exists in the database
        boolean applicantExists = jobPostRepository.existsById(request.getJobId());
        if (!applicantExists) {
            throw new UserException(ExceptionMessages.JOBPOST_NOT_FOUND, HttpStatus.NOT_FOUND);
        } 
        
        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobId(request.getJobId());
        jobApplication.setApplicantId(request.getApplicantId());
        if (request.getStatus() == null) {
            jobApplication.setStatus(JobApplicationStatus.PENDING);
        } else {
            jobApplication.setStatus(request.getStatus());
        }

        JobApplication savedApplication = jobApplicationRepository.save(jobApplication);
        log.info("Job application created for jobId: {} and applicantId: {}", request.getJobId(), request.getApplicantId());
        return mapToDTO(savedApplication);
    }

    public JobApplicationDTO updateJobApplicationStatus(JobApplicationDTO request) {
        log.info("Processing job application for jobId: {} and applicantId: {}", request.getJobId(), request.getApplicantId());

        // Check if the job_id exists in the database
        boolean applicantExists = jobPostRepository.existsById(request.getJobId());
        if (!applicantExists) {
            throw new UserException(ExceptionMessages.JOBPOST_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        JobApplication jobApplication = new JobApplication();
        jobApplication.setId(request.getId());
        jobApplication.setJobId(request.getJobId());
        jobApplication.setApplicantId(request.getApplicantId());
        if (request.getStatus() == null) {
            jobApplication.setStatus(JobApplicationStatus.PENDING);
        } else {
            jobApplication.setStatus(request.getStatus());
        }

        JobApplication savedApplication = jobApplicationRepository.save(jobApplication);
        log.info("Job application Update for jobId: {} and applicantId: {}", request.getJobId(), request.getApplicantId());
        return mapToDTO(savedApplication);
    }

    // Get all Job Applications for an applicant
    public List<JobApplicationDTO> getAllApplicationsByApplicant(Long applicantId) {
        log.info("Fetching all job applications for applicantId: {}", applicantId);
    	
    	// Check if the applicant exists in the database
        boolean applicantExists = applicantUserRepository.existsById(applicantId);
        if (!applicantExists) {
            throw new UserException(ExceptionMessages.APPLICANT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        List<JobApplication> applications = jobApplicationRepository.findByApplicantId(applicantId);
        return applications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<JobApplicationDTO> getAllApplicantsByJobId(Long jobId) {
        log.info("Fetching all job applications for Job Id: {}", jobId);

        List<JobApplication> applications = jobApplicationRepository.findByJobId(jobId);
        return applications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to map entity to DTO
    private JobApplicationDTO mapToDTO(JobApplication jobApplication) {
        return new JobApplicationDTO(
                jobApplication.getId(),
                jobApplication.getJobId(),
                jobApplication.getApplicantId(),
                jobApplication.getStatus()
        );
    }
}
