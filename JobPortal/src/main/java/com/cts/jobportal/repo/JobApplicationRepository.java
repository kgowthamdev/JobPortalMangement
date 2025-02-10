package com.cts.jobportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.jobportal.entity.JobApplication;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByApplicantId(Long applicationId);
    Optional<JobApplication> findByJobIdAndApplicantId(Long jobId, Long applicantId);
    List<JobApplication> findByJobId(Long jobId);
}
