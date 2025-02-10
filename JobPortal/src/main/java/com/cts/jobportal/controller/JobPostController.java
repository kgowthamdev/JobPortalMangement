package com.cts.jobportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.jobportal.dto.JobPostDTO;
import com.cts.jobportal.dto.JobPostsResponseDTO;
import com.cts.jobportal.service.JobPostService;

@RestController
@RequestMapping("/api/v1/companies")
public class JobPostController {

    private static final Logger log = LoggerFactory.getLogger(JobPostController.class);

    @Autowired
    private JobPostService jobPostService;

    // Create Job Post
    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping("/{company_id}/job_posts")
    public ResponseEntity<JobPostDTO> createJobPost(@PathVariable Long company_id, @RequestBody JobPostDTO jobPostDTO) {
        log.info("Creating job post for company_id: {}", company_id);
        jobPostDTO.setCompanyId(company_id);
        JobPostDTO createdJobPost = jobPostService.createJobPost(jobPostDTO);
        log.info("Job post created for company_id: {}", company_id);
        return new ResponseEntity<>(createdJobPost, HttpStatus.CREATED);
    }

    // Get All Job Posts
    @GetMapping("/{company_id}/job_posts")
    public ResponseEntity<JobPostsResponseDTO> getAllJobPosts(@PathVariable Long company_id) {
        log.info("Fetching all job posts for company_id: {}", company_id);
        JobPostsResponseDTO response = jobPostService.getAllJobPosts(company_id);
        log.info("Fetched {} job posts for company_id: {}", response.getJobPosts().size(), company_id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get All Job Posts
    @GetMapping("/jobs")
    public ResponseEntity<JobPostsResponseDTO> getAllJobPosts() {
        log.info("Fetching all job posts");
        JobPostsResponseDTO response = jobPostService.getAllJobPosts();
        log.info("Fetched job posts");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Get Job Post by ID
    @GetMapping("/{company_id}/job_posts/{job_id}")
    public ResponseEntity<JobPostDTO> getJobPostById(@PathVariable Long company_id, @PathVariable Long job_id) {
        log.info("Fetching job post with job_id: {} for company_id: {}", job_id, company_id);
        JobPostDTO jobPostDTO = jobPostService.getJobPostById(company_id, job_id);
        log.info("Fetched job post with job_id: {} for company_id: {}", job_id, company_id);
        return new ResponseEntity<>(jobPostDTO, HttpStatus.OK);
    }
    @GetMapping("/job_posts/{job_id}")
    public ResponseEntity<JobPostDTO> getJobPostByOnlyId(@PathVariable Long job_id) {
        log.info("Fetching job post with job_id: {}", job_id);
        JobPostDTO jobPostDTO = jobPostService.getJobPostById(job_id);
        log.info("Fetched job post with job_id: {}", job_id);
        return new ResponseEntity<>(jobPostDTO, HttpStatus.OK);
    }

    // Update Job Post
    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{company_id}/job_posts/{job_id}")
    public ResponseEntity<JobPostDTO> updateJobPost(@PathVariable Long company_id, @PathVariable Long job_id, @RequestBody JobPostDTO jobPostDTO) {
        log.info("Updating job post with job_id: {} for company_id: {}", job_id, company_id);
        JobPostDTO updatedJobPost = jobPostService.updateJobPost(company_id, job_id, jobPostDTO);
        log.info("Updated job post with job_id: {} for company_id: {}", job_id, company_id);
        return new ResponseEntity<>(updatedJobPost, HttpStatus.OK);
    }

    // Delete Job Post
    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/{company_id}/job_posts/{job_id}")
    public ResponseEntity<Void> deleteJobPost(@PathVariable Long company_id, @PathVariable Long job_id) {
        log.info("Deleting job post with job_id: {} for company_id: {}", job_id, company_id);
        jobPostService.deleteJobPost(company_id, job_id);
        log.info("Deleted job post with job_id: {} for company_id: {}", job_id, company_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
