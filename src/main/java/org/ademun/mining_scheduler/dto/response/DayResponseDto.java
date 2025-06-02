package org.ademun.mining_scheduler.dto.response;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.ademun.mining_scheduler.entity.Day}
 */
public record DayResponseDto(UUID id, DayOfWeek dayOfWeek, UUID schedule,
                             Set<UUID> lessons) implements
    Serializable {

}
