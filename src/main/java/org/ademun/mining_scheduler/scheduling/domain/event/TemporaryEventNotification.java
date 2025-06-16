package org.ademun.mining_scheduler.scheduling.domain.event;

import java.time.LocalDateTime;
import org.ademun.mining_scheduler.scheduling.domain.model.EventId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;

public record TemporaryEventNotification(ScheduleId scheduleId, EventId eventId, LocalDateTime date,
                                         NotificationType type) {

  public enum NotificationType {
    DAY_BEFORE, DAY_OF, START_TIME
  }
}
