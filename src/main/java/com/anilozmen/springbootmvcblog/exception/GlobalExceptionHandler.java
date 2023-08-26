package com.anilozmen.springbootmvcblog.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHandler {

  public static final String DEFAULT_ERROR_VIEW = "error";
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  protected ModelAndView handleNoHandlerFoundException(
      HttpServletRequest request,
      NoHandlerFoundException exception
  ) {
    logError(exception, request.getRequestURL().toString());
    return createModelAndView(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PostNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ModelAndView handlePostNotFoundException(
      HttpServletRequest request,
      PostNotFoundException exception
  ) {
    logError(exception, request.getRequestURL().toString());
    return createModelAndView(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public ModelAndView handleUserAlreadyExistException(
      HttpServletRequest request,
      UserAlreadyExistException exception
  ) {
    logError(exception, request.getRequestURL().toString());
    return createModelAndView(exception.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  public ModelAndView handleAccessDeniedException(
      HttpServletRequest request,
      AccessDeniedException exception
  ) {
    logError(exception, request.getRequestURL().toString());
    return createModelAndView(exception.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

    if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
      throw e;
    }

    logError(e, req.getRequestURL().toString());
    return createModelAndView(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  private void logError(Throwable e, String requestURL) {
    logger.error(e.getClass() + "Request: " + requestURL + " Error: " + e.getMessage());
  }

  private ModelAndView createModelAndView(String errorMessage, HttpStatus status) {
    ModelAndView modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
    modelAndView.addObject("errorMessage", errorMessage);
    modelAndView.addObject("httpStatus", status);
    return modelAndView;
  }
}