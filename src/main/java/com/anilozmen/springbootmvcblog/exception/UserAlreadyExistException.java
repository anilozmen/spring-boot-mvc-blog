package com.anilozmen.springbootmvcblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistException extends RuntimeException {

  public UserAlreadyExistException() {
    super("User already exist.");
  }
}
