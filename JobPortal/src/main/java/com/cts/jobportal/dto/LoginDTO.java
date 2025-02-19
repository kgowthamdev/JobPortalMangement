package com.cts.jobportal.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
	    @NotBlank(message = "Email is required")
	    @Email(message = "Invalid email format")
	    private String username;

	    @NotBlank(message = "Password is required")
	    private String password;

	    @NotBlank(message = "User type is required")
	    private String userType;
}