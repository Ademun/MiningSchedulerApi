package org.ademun.mining_scheduler.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.io.Serializable;

/**
 * DTO for {@link org.ademun.mining_scheduler.entity.Schedule}
 */
public record ScheduleRequestDto(
    @Min(message = "A week should be in range of 1-5", value = 1) @Max(message = "A week should be in range of 1-5", value = 5) short week) implements
    Serializable {

}
