package com.cts.jobportal.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;

import com.cts.jobportal.config.UserDetailsServiceImpl;
import com.cts.jobportal.constants.ExceptionMessages;
import com.cts.jobportal.constants.UserRole;
import com.cts.jobportal.dto.ApplicantUserDTO;
import com.cts.jobportal.dto.JwtResponseDTO;
import com.cts.jobportal.dto.LoginDTO;
import com.cts.jobportal.dto.RegisterDTO;
import com.cts.jobportal.entity.ApplicantUser;
import com.cts.jobportal.entity.CompanyUser;
import com.cts.jobportal.exception.UserException;
import com.cts.jobportal.repo.ApplicantUserRepository;
import com.cts.jobportal.repo.CompanyUserRepository;
import com.cts.jobportal.service.JwtService;
import com.cts.jobportal.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ApplicantUserRepository applicantUserRepository;

     @Autowired
     private CompanyUserRepository companyUserRepository;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody LoginDTO authrequestDTO) throws Exception{
        log.info("Attempting login for user: {}", authrequestDTO.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authrequestDTO.getUsername(), authrequestDTO.getPassword())
            );
            log.info("User authenticated successfully: {}", authrequestDTO.getUsername());
        } catch (BadCredentialsException e) {
            log.error("Authentication failed for user: {}", authrequestDTO.getUsername());
            throw new UserException(ExceptionMessages.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
        }

        String token = jwtService.GenerateToken(authrequestDTO.getUsername());
        log.info("JWT token generated for user: {}", authrequestDTO.getUsername());
        return new JwtResponseDTO(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        log.info("Attempting to register new user: {}", registerDTO.getUsername());
        try {
            userService.registerUser(registerDTO);
            log.info("User registered successfully: {}", registerDTO.getUsername());
            String token = jwtService.GenerateToken(registerDTO.getUsername());
            log.info("JWT token generated for user: {}", registerDTO.getUsername());
            return ResponseEntity.ok(new JwtResponseDTO(token));
        } catch (Exception e) {
            log.error("Error registering user: {}", registerDTO.getUsername(), e);
            throw new UserException("Error while Creating User", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<ApplicantUserDTO> getUser(@PathVariable String username) {
        log.info("Attempting to get user");
        ApplicantUser user = applicantUserRepository.findByUsername(username);
        CompanyUser companyUser = companyUserRepository.findByUsername(username);
        if (user == null && companyUser == null) {
            throw new UserException(ExceptionMessages.USERNAME_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        ApplicantUserDTO userDTO = new ApplicantUserDTO();
        if (user == null) {
            userDTO.setApplicantId(companyUser.getCompanyId());
            userDTO.setUsername(companyUser.getUsername());
            userDTO.setPassword(companyUser.getPassword());
            userDTO.setUserType(UserRole.EMPLOYER.toString());
        } else {
            userDTO.setApplicantId(user.getApplicantId());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTO.setUserType(UserRole.USER.toString());
        }
            return new ResponseEntity<ApplicantUserDTO>(userDTO, HttpStatus.OK);
        }
    }