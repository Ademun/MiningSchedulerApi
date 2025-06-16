package org.ademun.mining_scheduler.scheduling.application.usecase;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.AddEventCommand;
import org.ademun.mining_scheduler.scheduling.domain.model.Event;
import org.ademun.mining_scheduler.scheduling.domain.model.EventId;
import org.ademun.mining_scheduler.scheduling.domain.model.RecurringEvent;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.ademun.mining_scheduler.scheduling.domain.model.TemporaryEvent;
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
    Event event;
    if (command.isTemporary()) {
      if (command.date() == null) {
        throw new RuntimeException();
      }
      event = new TemporaryEvent();
      buildEvent(command, event);
      ((TemporaryEvent) event).setDate(command.date());
    } else {
      event = new RecurringEvent();
      buildEvent(command, event);
    }
    schedule.addEvent(command.weekIndex(), command.dayOfWeek(), event);
    return null;
  }

  private void buildEvent(AddEventCommand command, Event event) {
    event.setId(new EventId(UUID.randomUUID()));
    event.setTitle(command.title());
    event.setDescription(command.description());
    event.setTimePeriod(new TimePeriod(command.start(), command.end()));
  }
}
