package com.cts.jobportal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class UserProfileDTO {
    private Long id;
    private Long applicationId;
    private String firstname;
    private String lastname;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @Pattern(regexp = "\\d{10}", message = "Contact must be a 10-digit number")
    private String contact;
    private String location;
    private String dob;
    private String description;
    private List<String> skills;
    private String resume;
}
