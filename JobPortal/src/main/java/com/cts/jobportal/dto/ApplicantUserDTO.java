package com.cts.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantUserDTO {
	private Long applicantId;
    private String username;
    private String password;
    private String email;
    private String userType;
}
