package com.cts.jobportal.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.jobportal.constants.ExceptionMessages;
import com.cts.jobportal.constants.UserRole;
import com.cts.jobportal.dto.RegisterDTO;
import com.cts.jobportal.entity.ApplicantUser;
import com.cts.jobportal.entity.CompanyUser;
import com.cts.jobportal.entity.Roles;
import com.cts.jobportal.exception.UserException;
import com.cts.jobportal.repo.ApplicantUserRepository;
import com.cts.jobportal.repo.CompanyUserRepository;
import com.cts.jobportal.repo.RoleRepository;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ApplicantUserRepository applicantUserRepository;

    @Autowired
    private CompanyUserRepository companyUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDTO registerDTO) {
        log.info("Registering user with email: {}", registerDTO.getEmail());
        
        if (registerDTO.getUserType().equalsIgnoreCase("USER")) {
            if (applicantUserRepository.findByEmail(registerDTO.getEmail()) != null) {
                log.warn("Email already exists for user: {}", registerDTO.getEmail());
                throw new UserException(ExceptionMessages.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT);
            }
            
            ApplicantUser user = new ApplicantUser();
            user.setUsername(registerDTO.getUsername());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            Roles role = roleRepository.findByName(UserRole.USER);
            user.setRoles(Set.of(role));
            applicantUserRepository.save(user);
            log.info("User registered successfully with email: {}", registerDTO.getEmail());
        } 
        else if (registerDTO.getUserType().equalsIgnoreCase("EMPLOYER")) {
            if (companyUserRepository.findByEmail(registerDTO.getEmail()) != null) {
                log.warn("Email already exists for employer: {}", registerDTO.getEmail());
                throw new UserException(ExceptionMessages.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT);
            }
            
            CompanyUser user = new CompanyUser();
            user.setUsername(registerDTO.getUsername());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            Roles role = roleRepository.findByName(UserRole.EMPLOYER);
            user.setRoles(Set.of(role));
            companyUserRepository.save(user);
            log.info("Employer registered successfully with email: {}", registerDTO.getEmail());
        }
    }
}