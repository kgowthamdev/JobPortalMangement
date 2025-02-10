package com.cts.jobportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.jobportal.entity.ApplicantUser;

public interface ApplicantUserRepository extends JpaRepository<ApplicantUser, Long> {
    ApplicantUser findByEmail(String email);
    ApplicantUser findByUsername(String username);
}
