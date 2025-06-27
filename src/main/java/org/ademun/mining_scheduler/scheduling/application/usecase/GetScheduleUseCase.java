package org.ademun.mining_scheduler.scheduling.application.usecase;

import java.util.Comparator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetScheduleResponse;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetScheduleResponse.WeekDto;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetScheduleResponse.WeekDto.DayDto;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetScheduleResponse.WeekDto.DayDto.EventDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetScheduleUseCase implements UseCase<UUID, GetScheduleResponse> {

  private final ScheduleRepository scheduleRepository;

  @Override
  public GetScheduleResponse execute(UUID scheduleId) {
    Schedule schedule = scheduleRepository.findById(new ScheduleId(scheduleId))
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format("Schedule with value %s not found", scheduleId)));
    return new GetScheduleResponse(schedule.getId().value(), schedule.getName(), schedule.getWeeks()
        .stream()
        .map(week -> new WeekDto(week.getId().value(), week.getAllDays()
            .stream()
            .map(day -> new DayDto(day.getId().value(), day.getDayOfWeek(), day.getAllEvents()
                .stream()
                .map(event -> new EventDto(event.getId().value(), event.getTitle(),
                    event.getDescription(), event.getTimePeriod().start(),
                    event.getTimePeriod().end()))
                .toList())).sorted(Comparator.comparing(DayDto::dayOfWeek))
            .toList()))
        .toList());
  }
}
