package com.cts.jobportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.jobportal.constants.ExceptionMessages;
import com.cts.jobportal.dto.JobPostDTO;
import com.cts.jobportal.dto.JobPostsResponseDTO;
import com.cts.jobportal.entity.JobPost;
import com.cts.jobportal.exception.UserException;
import com.cts.jobportal.repo.CompanyUserRepository;
import com.cts.jobportal.repo.JobPostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;
    
    @Autowired
    private CompanyUserRepository companyUserRepository;
    
    private static final Logger log = LoggerFactory.getLogger(JobPostService.class);

    // Create Job Post
    public JobPostDTO createJobPost(JobPostDTO jobPostDTO) {
        log.info("Creating new job post for company: {}", jobPostDTO.getCompanyId());
        try {
            JobPost savedJobPost = jobPostRepository.save(mapToEntity(jobPostDTO));
            log.info("Successfully created job post with id: {}", savedJobPost.getId());
            return mapToDTO(savedJobPost);
        } catch (Exception e) {
            log.error("Error creating job post for company: {}", jobPostDTO.getCompanyId(), e);
            throw e;
        }
    }
    
 // Get All Job Posts
    public JobPostsResponseDTO getAllJobPosts(Long company_id) {
        log.info("Fetching all job Posts for Company ID: {}", company_id);
    	// Check if the company exists in the database
        boolean applicantExists = companyUserRepository.existsById(company_id);
        if (!applicantExists) {
            log.info("No Company Exists");
            throw new UserException(ExceptionMessages.COMPANY_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        
        List<JobPost> jobPost = jobPostRepository.findByCompanyId(company_id);
        List<JobPostDTO> jobPostDTOs = jobPost.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JobPostsResponseDTO(jobPostDTOs.size(), jobPostDTOs);
    }

    public JobPostsResponseDTO getAllJobPosts() {
        log.info("Fetching all job Posts");

        List<JobPost> jobPost = jobPostRepository.findAll();
        List<JobPostDTO> jobPostDTOs = jobPost.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JobPostsResponseDTO(jobPostDTOs.size(), jobPostDTOs);
    }
    
    // Get Job Post by ID
    public JobPostDTO getJobPostById(Long company_id, Long job_id) {
    	
    	// Check if the company exists in the database
        boolean applicantExists = companyUserRepository.existsById(company_id);
        if (!applicantExists) {
            throw new UserException(ExceptionMessages.COMPANY_NOT_FOUND, HttpStatus.NOT_FOUND);
        } 
        
        JobPost jobPost = jobPostRepository.findByIdAndCompanyId(job_id,company_id)
                .orElseThrow(() -> new UserException(ExceptionMessages.JOBPOST_NOT_FOUND,HttpStatus.NOT_FOUND));
        return mapToDTO(jobPost);
    }

    public JobPostDTO getJobPostById(Long job_id) {
        JobPost jobPost = jobPostRepository.findById(job_id)
                .orElseThrow(() -> new UserException(ExceptionMessages.JOBPOST_NOT_FOUND,HttpStatus.NOT_FOUND));
        return mapToDTO(jobPost);
    }

    // Update Job Post
    @Transactional
    public JobPostDTO updateJobPost(Long company_id, Long jobId, JobPostDTO updatedJobPostDTO) {
        JobPost existingJobPost = jobPostRepository.findByIdAndCompanyId(jobId,company_id)
                .orElseThrow(() -> new UserException(ExceptionMessages.JOBPOST_NOT_FOUND,HttpStatus.NOT_FOUND));

        existingJobPost.setJobTitle(updatedJobPostDTO.getJobTitle());
        existingJobPost.setSkills(updatedJobPostDTO.getSkills());
        existingJobPost.setDescription(updatedJobPostDTO.getDescription());
        existingJobPost.setWorkExperience(updatedJobPostDTO.getWorkExperience());
        existingJobPost.setSalaryRange(updatedJobPostDTO.getSalaryRange());
        existingJobPost.setLocation(updatedJobPostDTO.getLocation());
        existingJobPost.setEmploymentType(updatedJobPostDTO.getEmploymentType());
        existingJobPost.setNoApplied(updatedJobPostDTO.getNoApplied());

        JobPost updatedJobPost = jobPostRepository.save(existingJobPost);
        log.info("Job post updated for companyId: {} and jobId: {}", company_id, jobId);
        return mapToDTO(updatedJobPost);
    }

    // Delete Job Post
    public void deleteJobPost(Long company_id, Long jobId) {
    	JobPost jobPost = jobPostRepository.findByIdAndCompanyId(jobId,company_id)
    			.orElseThrow(() -> new UserException(ExceptionMessages.JOBPOST_NOT_FOUND,HttpStatus.NOT_FOUND));
        jobPostRepository.delete(jobPost);
        log.info("Job post deleted for companyId: {} and jobId: {}", company_id, jobId);
    }

    // Mapping methods
    private JobPost mapToEntity(JobPostDTO dto) {
        return new JobPost(
                dto.getId(),
                dto.getCompanyId(),
                dto.getJobTitle(),
                dto.getSkills(),
                dto.getDescription(),
                dto.getWorkExperience(),
                dto.getSalaryRange(),
                dto.getLocation(),
                dto.getEmploymentType(),
                dto.getNoApplied(),
                dto.getPostedOn()
        );
    }

    private JobPostDTO mapToDTO(JobPost entity) {
        return new JobPostDTO(
                entity.getId(),
                entity.getCompanyId(),
                entity.getJobTitle(),
                entity.getSkills(),
                entity.getDescription(),
                entity.getWorkExperience(),
                entity.getSalaryRange(),
                entity.getLocation(),
                entity.getEmploymentType(),
                entity.getNoApplied(),
                entity.getPostedOn()
        );
    }
}
