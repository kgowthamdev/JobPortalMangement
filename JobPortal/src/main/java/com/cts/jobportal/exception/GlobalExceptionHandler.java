package com.cts.jobportal.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.jobportal.dto.ErrorResponseDTO;


@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(UserException.class) 
	 private ResponseEntity<ErrorResponseDTO> handleRuntimeException (UserException ex) {
		ErrorResponseDTO errorResponse = new ErrorResponseDTO();
		errorResponse.setErrorCode(ex.getHttpStatus().value());
		errorResponse.setErrorMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
	}
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach((error) -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        return ResponseEntity.badRequest().body(errors);
	    }
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex, WebRequest request) {
//		ErrorResponseDTO errorResponse = new ErrorResponseDTO();
//		errorResponse.setErrorCode(500); // Internal Server Error
//		errorResponse.setErrorMessage("An unexpected error occurred: " + ex.getMessage());
//		return ResponseEntity.status(500).body(errorResponse);
//	}
}
