package org.ademun.mining_scheduler.scheduling.application.usecase;

import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.domain.model.Day;
import org.ademun.mining_scheduler.scheduling.domain.model.DayId;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.ademun.mining_scheduler.scheduling.domain.model.Week;
import org.ademun.mining_scheduler.scheduling.domain.model.WeekId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddWeekUseCase implements UseCase<UUID, Void> {

  private final ScheduleRepository scheduleRepository;

  @Override
  @Transactional
  public Void execute(UUID scheduleId) {
    Schedule schedule = scheduleRepository.findById(new ScheduleId(scheduleId)).orElseThrow();
    Week week = appendDays(Week.builder().id(new WeekId(UUID.randomUUID())).build());
    schedule.addWeek(week);
    scheduleRepository.save(schedule);
    return null;
  }

  private Week appendDays(Week week) {
    final int MAX_DAYS = 7;
    List<Day> days = new ArrayList<>(MAX_DAYS);
    for (int i = 0; i < MAX_DAYS; i++) {
      Day day = Day.builder()
          .id(new DayId(UUID.randomUUID()))
          .dayOfWeek(DayOfWeek.of(i + 1))
          .week(week)
          .build();
      days.add(day);
    }
    week.setDays(days);
    return week;
  }
}
