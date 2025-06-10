package org.ademun.mining_scheduler.scheduling.application.usecase;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.AddEventCommand;
import org.ademun.mining_scheduler.scheduling.domain.model.Event;
import org.ademun.mining_scheduler.scheduling.domain.model.EventId;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.ademun.mining_scheduler.scheduling.domain.model.TimePeriod;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEventUseCase implements UseCase<AddEventCommand, Void> {

  private final ScheduleRepository scheduleRepository;

  @Override
  @Transactional
  public Void execute(AddEventCommand command) {
    Schedule schedule = scheduleRepository.findById(new ScheduleId(command.scheduleId()))
        .orElseThrow();
    Event event = Event.builder()
        .id(new EventId(UUID.randomUUID()))
        .title(command.title())
        .description(command.description())
        .timePeriod(new TimePeriod(command.start(), command.end()))
        .build();
    schedule.addEvent(command.weekIndex(), command.dayOfWeek(), event);
    scheduleRepository.save(schedule);
    return null;
  }
}
