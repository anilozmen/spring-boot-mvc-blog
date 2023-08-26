package com.anilozmen.springbootmvcblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException() {
    super("Post not found.");
  }
}