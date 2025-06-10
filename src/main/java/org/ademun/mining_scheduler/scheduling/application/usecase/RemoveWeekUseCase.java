package org.ademun.mining_scheduler.scheduling.application.usecase;

import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.RemoveWeekCommand;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveWeekUseCase implements UseCase<RemoveWeekCommand, Void> {

  private final ScheduleRepository scheduleRepository;

  @Override
  public Void execute(RemoveWeekCommand command) {
    Schedule schedule = scheduleRepository.findById(new ScheduleId(command.scheduleId()))
        .orElseThrow();
    schedule.removeWeek(command.weekIndex());
    scheduleRepository.save(schedule);
    return null;
  }
}
