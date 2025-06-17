package org.ademun.mining_scheduler.scheduling.application.usecase;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.ademun.mining_scheduler.scheduling.domain.model.TemporaryEvent;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetEventResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTodayEventsUseCase implements UseCase<UUID, List<GetEventResponse>> {

  private final ScheduleRepository scheduleRepository;

  @Override
  public List<GetEventResponse> execute(UUID scheduleId) {
    Schedule schedule = scheduleRepository.findById(new ScheduleId(scheduleId))
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format("Schedule with value %s not found", scheduleId)));
    return schedule.getTodayEvents()
        .stream()
        .map(event -> new GetEventResponse(event.getId(), event.getTitle(), event.getDescription(),
            event.getTimePeriod().start(), event.getTimePeriod().end(),
            event instanceof TemporaryEvent,
            event instanceof TemporaryEvent temp ? temp.getDate() : null))
        .toList();
  }
}
