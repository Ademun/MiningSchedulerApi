package org.ademun.mining_scheduler.scheduling.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.domain.service.ScheduleWeekRotationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ScheduleWeekRotationScheduler {

  private final ScheduleWeekRotationService rotationService;

  @Scheduled(cron = "0 59 23 ? * SUN")
  public void rotateSchedules() {
    rotationService.rotateActiveWeeks();
  }

}
