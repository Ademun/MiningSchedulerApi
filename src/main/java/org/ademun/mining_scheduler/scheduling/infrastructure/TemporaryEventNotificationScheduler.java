package org.ademun.mining_scheduler.scheduling.infrastructure;

import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.domain.service.TemporaryEventNotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemporaryEventNotificationScheduler {

  private final TemporaryEventNotificationService notificationService;

  @Scheduled(fixedRate = 120)
  public void checkEvents() {
    notificationService.checkAndSendNotifications();
  }
}
