package org.ademun.mining_scheduler.scheduling.application.usecase.command;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.UUID;

public record GetEventsCommand(@NotNull UUID scheduleId, @NotNull Integer weekIndex,
                               @NotNull DayOfWeek dayOfWeek) implements Serializable {

}
