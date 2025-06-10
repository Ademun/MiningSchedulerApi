package org.ademun.mining_scheduler.scheduling.application.usecase.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public record AddEventCommand(
    @NotNull
    UUID scheduleId,
    @NotNull
    Integer weekNumber,
    @NotNull
    DayOfWeek dayOfWeek,
    @NotBlank
    @Size(min = 1, max = 255)
    String title,
    @NotBlank
    @Size(min = 1, max = 255)
    String description,
    @NotNull
    LocalTime start,
    @NotNull
    LocalTime end) implements
    Serializable {

}
