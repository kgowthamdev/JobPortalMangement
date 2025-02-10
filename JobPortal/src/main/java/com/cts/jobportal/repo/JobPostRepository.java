package com.cts.jobportal.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.jobportal.entity.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
	List<JobPost> findByCompanyId(Long company_id);
    Optional<JobPost> findByIdAndCompanyId(Long job_id,Long company_id);
}
