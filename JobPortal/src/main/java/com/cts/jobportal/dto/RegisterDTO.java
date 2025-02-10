package com.cts.jobportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
	
		@NotBlank(message = "Username is required")
	    private String username;
		
		@NotBlank(message = "Email is required")
	    @Email(message = "Invalid email format")
	    private String email;
		
		@NotBlank(message = "Password is required")
	    private String password;
		
		@NotBlank(message = "User type is required")
	    private String userType; // This can be "USER" or "EMPLOYER"

}
