package org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * DTO for {@link org.ademun.mining_scheduler.scheduling.domain.Event}
 */
public record AddEventRequest(@NotBlank @Size(min = 1, max = 255) String title,
                              @NotBlank @Size(min = 1, max = 255) String description,
                              @NotNull LocalTime start, @NotNull LocalTime end) implements
    Serializable {

}
