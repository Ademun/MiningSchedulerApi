package org.ademun.mining_scheduler.scheduling.domain.model;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

public record DayId(@NotNull UUID value) implements Serializable {

}
