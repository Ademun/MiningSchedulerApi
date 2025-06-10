package org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;

/**
 * DTO for {@link Schedule}
 */
public record CreateScheduleRequest(@Size(min = 1) @NotBlank String name) implements Serializable {

}
