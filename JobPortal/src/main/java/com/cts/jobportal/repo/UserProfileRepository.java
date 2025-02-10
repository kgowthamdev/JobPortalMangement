package com.cts.jobportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.jobportal.entity.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    List<UserProfile> findByApplicantId(Long applicationId);

    Optional<UserProfile> findByIdAndApplicantId(Long profileId,Long applicationId);

	boolean existsByEmail(String email);
	boolean existsByContact(String contact);
	
}
