package org.ademun.mining_scheduler.exception;

public class ResourceIsBeingUsedException extends RuntimeException {

  public ResourceIsBeingUsedException(String message) {
    super(message);
  }
}
