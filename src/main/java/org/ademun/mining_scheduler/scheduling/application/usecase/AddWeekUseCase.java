package org.ademun.mining_scheduler.scheduling.application.usecase;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.ademun.mining_scheduler.scheduling.domain.model.Week;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddWeekUseCase implements UseCase<UUID, Void> {

  private final ScheduleRepository scheduleRepository;

  @Override
  @Transactional
  public Void execute(UUID scheduleId) {
    Schedule schedule = scheduleRepository.findById(new ScheduleId(scheduleId))
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format("Schedule with value %s not found", scheduleId)));
    Week week = new Week();
    schedule.addWeek(week);
    System.out.println(week.toString());
    System.out.println(schedule.toString());
    scheduleRepository.save(schedule);
    return null;
  }
}
