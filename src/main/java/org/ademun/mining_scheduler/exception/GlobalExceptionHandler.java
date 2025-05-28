package org.ademun.mining_scheduler.exception;

import org.ademun.mining_scheduler.dto.response.ErrorResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public HttpEntity<ErrorResponse> handleResourceAlreadyExistsException(
      ResourceAlreadyExistsException e) {
    ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    return new ResponseEntity<>(response, response.code());
  }

  @ExceptionHandler(ResourceIsBeingUsedException.class)
  public HttpEntity<ErrorResponse> handleResourceIsBeingUsedException(
      ResourceIsBeingUsedException e) {
    ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    return new ResponseEntity<>(response, response.code());
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public HttpEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
    ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    return new ResponseEntity<>(response, response.code());
  }

  @ExceptionHandler(Exception.class)
  public HttpEntity<ErrorResponse> handleException(Exception e) {
    ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    return new ResponseEntity<>(response, response.code());
  }
}
