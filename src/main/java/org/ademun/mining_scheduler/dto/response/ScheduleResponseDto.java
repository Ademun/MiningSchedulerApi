package org.ademun.mining_scheduler.dto.response;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.ademun.mining_scheduler.entity.Schedule}
 */
public record ScheduleResponseDto(UUID id, Short week, UUID group, Set<UUID> dayIds) implements
    Serializable {

}
