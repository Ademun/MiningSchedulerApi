package org.ademun.mining_scheduler.scheduling.domain.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.domain.event.TemporaryEventNotification.NotificationType;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.ademun.mining_scheduler.scheduling.domain.model.TemporaryEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemporaryEventNotificationService {

  private final ScheduleRepository scheduleRepository;
  private final Clock clock;

  public void checkAndSendNotifications() {
    List<Schedule> schedules = scheduleRepository.findAll();
    schedules.forEach(this::checkScheduleEvents);
  }

  private void checkScheduleEvents(Schedule schedule) {
    schedule.getWeeks()
        .forEach(week -> week.getAllDays()
            .forEach(day -> day.getAllEvents()
                .stream()
                .filter(event -> event instanceof TemporaryEvent)
                .forEach(event -> checkEvent(schedule.getId(), (TemporaryEvent) event))));
  }

  private void checkEvent(ScheduleId scheduleId, TemporaryEvent event) {
    LocalDateTime now = LocalDateTime.now(clock);

    LocalDateTime eventStart = event.getStartDateTime();

    if (shouldNotifyDayBefore(eventStart, now)) {
      publishNotification(scheduleId, event, NotificationType.DAY_BEFORE);
    }

    if (shouldNotifyDayOf(eventStart, now)) {
      publishNotification(scheduleId, event, NotificationType.DAY_OF);
    }

    if (shouldNotifyStartTime(eventStart, now)) {
      publishNotification(scheduleId, event, NotificationType.START_TIME);
    }
  }

  private boolean shouldNotifyDayBefore(LocalDateTime eventStart, LocalDateTime now) {
    LocalDateTime notificationTime = eventStart.minusDays(1).withHour(12).withMinute(0);
    return isWithinTimeWindow(now, notificationTime);
  }

  private boolean shouldNotifyDayOf(LocalDateTime eventStart, LocalDateTime now) {
    LocalDateTime notificationTime = eventStart.toLocalDate().atStartOfDay().plusHours(8);
    return isWithinTimeWindow(now, notificationTime);
  }

  private boolean shouldNotifyStartTime(LocalDateTime eventStart, LocalDateTime now) {
    return now.isAfter(eventStart.minusMinutes(2)) && now.isBefore(eventStart.plusMinutes(2));
  }

  private boolean isWithinTimeWindow(LocalDateTime current, LocalDateTime target) {
    return current.isAfter(target) && current.isBefore(target.plusMinutes(1));
  }

  private void publishNotification(ScheduleId scheduleId, TemporaryEvent event,
      NotificationType type) {
  }
}
