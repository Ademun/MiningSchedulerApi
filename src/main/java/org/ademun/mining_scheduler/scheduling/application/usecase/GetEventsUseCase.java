package org.ademun.mining_scheduler.scheduling.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.GetEventsCommand;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.ademun.mining_scheduler.scheduling.domain.model.TemporaryEvent;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetEventResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEventsUseCase implements UseCase<GetEventsCommand, List<GetEventResponse>> {

  private final ScheduleRepository scheduleRepository;

  @Override
  public List<GetEventResponse> execute(GetEventsCommand command) {
    Schedule schedule = scheduleRepository.findById(new ScheduleId(command.scheduleId()))
        .orElseThrow();
    return schedule.getDayByWeekByDayOfWeek(command.weekIndex(), command.dayOfWeek())
        .getAllEvents()
        .stream()
        .map(event -> new GetEventResponse(event.getId(), event.getTitle(), event.getDescription(),
            event.getTimePeriod().start(), event.getTimePeriod().end(),
            event instanceof TemporaryEvent,
            event instanceof TemporaryEvent temp ? temp.getDate() : null, command.weekIndex(),
            command.dayOfWeek()))
        .toList();
  }
}
