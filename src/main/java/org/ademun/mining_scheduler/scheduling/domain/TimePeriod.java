package org.ademun.mining_scheduler.scheduling.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalTime;

@Embeddable
public record TimePeriod(@NotNull LocalTime start,
                         @NotNull LocalTime end) {

  public TimePeriod(LocalTime start, LocalTime end) {
    if (start.isAfter(end)) {
      throw new RuntimeException();
    }
    this.start = start;
    this.end = end;
  }

  public Duration duration() {
    return Duration.between(start, end);
  }

  public boolean overlapsWith(@NotNull TimePeriod other) {
    return start.isBefore(other.end) && end.isAfter(other.start);
  }
}
