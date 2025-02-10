package com.cts.jobportal.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException{
  private final HttpStatus httpStatus;
  public UserException(String s, HttpStatus httpStatus) {
	  super(s);
	  this.httpStatus=httpStatus;
  }

}
