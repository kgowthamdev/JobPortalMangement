package com.cts.jobportal.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProfileDTO {
	private Long id;
    private Long companyId;
    private String companyName;
    private String domain;
    private String description;
    private String location;
    
    @Pattern(regexp = "\\d{10}", message = "Contact must be a 10-digit number")
    private String contact;

}
