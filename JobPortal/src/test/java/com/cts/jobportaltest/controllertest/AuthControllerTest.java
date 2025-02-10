package com.cts.jobportaltest.controllertest;



import com.cts.jobportal.controller.AuthController;
import com.cts.jobportal.dto.JwtResponseDTO;
import com.cts.jobportal.dto.LoginDTO;
import com.cts.jobportal.dto.RegisterDTO;
import com.cts.jobportal.exception.UserException;
import com.cts.jobportal.service.JwtService;
 import com.cts.jobportal.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateAndGetToken_Success() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("password");

        when(jwtService.GenerateToken(anyString())).thenReturn("token");

        JwtResponseDTO response = authController.AuthenticateAndGetToken(loginDTO);

        assertNotNull(response);
        assertEquals("token", response.getAccessToken());
    }

    @Test
    public void testAuthenticateAndGetToken_Failure() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(UserException.class, () -> authController.AuthenticateAndGetToken(loginDTO));
    }

    @Test
    public void testRegisterUser_Success() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("password");

        when(jwtService.GenerateToken(anyString())).thenReturn("token");

        ResponseEntity<?> response = authController.registerUser(registerDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", ((JwtResponseDTO) response.getBody()).getAccessToken());
    }

    @Test
    public void testRegisterUser_Failure() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("password");

        // Use doThrow for void methods
        doThrow(new RuntimeException("Error")).when(userService).registerUser(any(RegisterDTO.class));

        assertThrows(UserException.class, () -> authController.registerUser(registerDTO));
    }
}