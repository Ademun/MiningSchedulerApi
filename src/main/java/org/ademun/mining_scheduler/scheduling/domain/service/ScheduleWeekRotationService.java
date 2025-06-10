package org.ademun.mining_scheduler.scheduling.domain.service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleWeekRotationService {

  private final ScheduleRepository scheduleRepository;
  private final Clock clock;

  public void rotateActiveWeeks() {
    LocalDate today = LocalDate.now(clock);
    if (today.getDayOfWeek() != DayOfWeek.SUNDAY) {
      return;
    }

    List<Schedule> schedules = scheduleRepository.findAll();

    schedules.forEach(schedule -> {
      schedule.rotateWeek();
      scheduleRepository.save(schedule);
    });
  }
}

