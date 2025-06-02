package org.ademun.mining_scheduler.dto.mapper;

import java.util.stream.Collectors;
import org.ademun.mining_scheduler.dto.response.DayResponseDto;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Lesson;
import org.springframework.stereotype.Component;

@Component
public class DayMapper {

//  public Day fromRequest(DayRequestDto requestDto) {
//    Day day = new Day();
//    day.setWeek(requestDto.week());
//    return day;
//  }

  public DayResponseDto toResponse(Day day) {
    return new DayResponseDto(day.getId(), day.getDayOfWeek(),
        day.getSchedule().getId(),
        day.getLessons().stream().map(Lesson::getId).collect(Collectors.toSet()));
  }
}
