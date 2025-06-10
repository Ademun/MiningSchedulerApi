package org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link org.ademun.mining_scheduler.scheduling.domain.Schedule}
 */
public record CreateScheduleRequest(@Size(min = 1) @NotBlank String name) implements Serializable {

}
