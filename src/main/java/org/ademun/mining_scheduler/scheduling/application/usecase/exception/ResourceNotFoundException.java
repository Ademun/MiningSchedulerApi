package org.ademun.mining_scheduler.scheduling.application.usecase.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
