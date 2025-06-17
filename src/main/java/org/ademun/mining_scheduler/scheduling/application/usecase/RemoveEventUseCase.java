package org.ademun.mining_scheduler.scheduling.application.usecase;

import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.RemoveEventCommand;
import org.ademun.mining_scheduler.scheduling.application.usecase.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.scheduling.domain.model.EventId;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveEventUseCase implements UseCase<RemoveEventCommand, Void> {

  private final ScheduleRepository scheduleRepository;

  @Override
  public Void execute(RemoveEventCommand command) {
    Schedule schedule = scheduleRepository.findById(new ScheduleId(command.scheduleId()))
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format("Schedule with value %s not found", command.scheduleId())));
    schedule.removeEvent(new EventId(command.eventId()));
    scheduleRepository.save(schedule);
    return null;
  }
}
