package org.ademun.mining_scheduler.scheduling.application.usecase;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
    Week week = new Week();
    week.setId(new WeekId(UUID.randomUUID()));
    week.setSchedule(schedule);
    schedule.addWeek(week);
    scheduleRepository.save(schedule);
    return null;
  }
}
