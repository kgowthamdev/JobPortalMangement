package com.cts.jobportal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.jobportal.constants.UserRole;
import com.cts.jobportal.entity.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles findByName(UserRole name);
}