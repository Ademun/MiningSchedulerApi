package org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import org.ademun.mining_scheduler.scheduling.domain.model.EventId;

public record GetEventResponse(EventId id, String title, String description,
                               LocalTime timePeriodStart, LocalTime timePeriodEnd,
                               Boolean isTemporary, LocalDate date, Integer weekIndex,
                               DayOfWeek dayOfWeek) implements
    Serializable {

}
