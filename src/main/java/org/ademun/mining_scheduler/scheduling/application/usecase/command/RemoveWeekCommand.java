package org.ademun.mining_scheduler.scheduling.application.usecase.command;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record RemoveWeekCommand(@NotNull UUID scheduleId, @NotNull Integer weekNumber) {

}
