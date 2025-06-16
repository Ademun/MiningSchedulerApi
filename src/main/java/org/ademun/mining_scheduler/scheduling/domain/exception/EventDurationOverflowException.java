package org.ademun.mining_scheduler.scheduling.domain.exception;

public class EventDurationOverflowException extends RuntimeException {

  public EventDurationOverflowException(String message) {
    super(message);
  }
}
