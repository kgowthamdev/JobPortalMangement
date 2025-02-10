package com.cts.jobportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.jobportal.entity.CompanyProfile;

import java.util.List;
import java.util.Optional;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {

    List<CompanyProfile> findByCompanyId(Long companyId);

    Optional<CompanyProfile> findByCompanyIdAndId(Long companyId, Long profileId);

	boolean existsByContact(String contact);
}

