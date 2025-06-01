package org.ademun.mining_scheduler.dto.mapper;

import java.util.stream.Collectors;
import org.ademun.mining_scheduler.dto.request.ScheduleRequestDto;
import org.ademun.mining_scheduler.dto.response.ScheduleResponseDto;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Schedule;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

  public Schedule fromRequest(ScheduleRequestDto requestDto) {
    Schedule schedule = new Schedule();
    schedule.setWeek(requestDto.week());
    return schedule;
  }

  public ScheduleResponseDto toResponse(Schedule schedule) {
    return new ScheduleResponseDto(schedule.getId(), schedule.getWeek(),
        schedule.getGroup().getId(),
        schedule.getDays().stream().map(Day::getId).collect(Collectors.toSet()));
  }
}
