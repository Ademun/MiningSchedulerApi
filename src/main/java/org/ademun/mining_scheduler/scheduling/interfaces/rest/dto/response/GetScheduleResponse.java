package org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record GetScheduleResponse(UUID id, String name, List<WeekDto> weeks) implements
    Serializable {

  public record WeekDto(UUID id, List<DayDto> days) implements Serializable {

    public record DayDto(UUID id, DayOfWeek dayOfWeek, List<EventDto> events) implements
        Serializable {

      public record EventDto(UUID id, String title, String description, LocalTime timePeriodStart,
                             LocalTime timePeriodEnd) implements Serializable {

      }
    }
  }
}
