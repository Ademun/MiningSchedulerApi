package org.ademun.mining_scheduler.scheduling.application.usecase;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.AddEventCommand;
import org.ademun.mining_scheduler.scheduling.application.usecase.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.scheduling.domain.exception.ModelInvariantException;
import org.ademun.mining_scheduler.scheduling.domain.model.Event;
import org.ademun.mining_scheduler.scheduling.domain.model.EventId;
import org.ademun.mining_scheduler.scheduling.domain.model.RecurringEvent;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.ademun.mining_scheduler.scheduling.domain.model.TemporaryEvent;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.AddEventResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEventUseCase implements UseCase<AddEventCommand, AddEventResponse> {

  private final ScheduleRepository scheduleRepository;

  @Override
  @Transactional
  public AddEventResponse execute(AddEventCommand command) {
    Schedule schedule = scheduleRepository.findById(new ScheduleId(command.scheduleId()))
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format("Schedule with value %s not found", command.scheduleId())));
    Event event;
    if (command.isTemporary()) {
      if (command.date() == null) {
        throw new ModelInvariantException("A temporary event must have a date");
      }
      event = new TemporaryEvent();
      buildEvent(command, event);
      ((TemporaryEvent) event).setDate(command.date());
    } else {
      event = new RecurringEvent();
      buildEvent(command, event);
    }
    schedule.addEvent(command.weekIndex(), command.dayOfWeek(), event);
    return new AddEventResponse(event.getId().value());
  }

  private void buildEvent(AddEventCommand command, Event event) {
    event.setId(new EventId(UUID.randomUUID()));
    event.setTitle(command.title());
    event.setDescription(command.description());
    event.setTimePeriod(command.timePeriod());
  }
}
