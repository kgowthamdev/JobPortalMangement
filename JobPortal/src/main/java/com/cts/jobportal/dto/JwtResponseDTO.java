// package com.cts.jobportal.dto;



// public class JwtResponseDTO {
//     private String accessToken;

//     public JwtResponseDTO(){
    	
//     }
    
// 	public JwtResponseDTO(String generateToken) {
// 		accessToken = generateToken;
// 	}

// 	public String getAccessToken() {
// 		return accessToken;
// 	}

// 	public void setAccessToken(String accessToken) {
// 		this.accessToken = accessToken;
// 	}
  
// }
package com.cts.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor      // Generates a no-argument constructor
@AllArgsConstructor     // Generates a constructor with all fields as parameters
public class JwtResponseDTO {
    private String accessToken;
}