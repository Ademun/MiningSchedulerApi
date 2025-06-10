package org.ademun.mining_scheduler.scheduling.application.usecase.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

public record CreateScheduleCommand(@NotBlank @Size(min = 1, max = 255) String name) implements
    Serializable {

}
