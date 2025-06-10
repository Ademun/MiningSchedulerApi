package org.ademun.mining_scheduler.scheduling.application.usecase;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetScheduleResponse;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetScheduleResponse.WeekDto;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetScheduleResponse.WeekDto.DayDto;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetScheduleResponse.WeekDto.DayDto.EventDto;
import org.ademun.mining_scheduler.scheduling.domain.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetScheduleUseCase implements UseCase<UUID, GetScheduleResponse> {

  private final ScheduleRepository scheduleRepository;

  @Override
  public GetScheduleResponse execute(UUID scheduleId) {
    Schedule schedule = scheduleRepository.findById(new ScheduleId(scheduleId)).orElseThrow();
    return new GetScheduleResponse(schedule.getId().id(), schedule.getName(), schedule.getWeeks()
        .stream()
        .map(week -> new WeekDto(week.getId().id(), week.getDays()
            .stream()
            .map(day -> new DayDto(day.getId().id(), day.getDayOfWeek(), day.getEvents()
                .stream()
                .map(event -> new EventDto(event.getId().id(), event.getTitle(),
                    event.getDescription(), event.getTimePeriod().start(),
                    event.getTimePeriod().end()))
                .toList()))
            .toList()))
        .toList());
  }
}
