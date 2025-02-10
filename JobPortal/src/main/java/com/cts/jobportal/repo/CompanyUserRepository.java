package com.cts.jobportal.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.jobportal.entity.CompanyUser;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
    CompanyUser findByEmail(String email);
    CompanyUser findByUsername(String username);
}
