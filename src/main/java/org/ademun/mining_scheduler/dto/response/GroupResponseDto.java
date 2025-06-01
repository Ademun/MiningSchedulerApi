package org.ademun.mining_scheduler.dto.response;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.ademun.mining_scheduler.entity.Group}
 */
public record GroupResponseDto(UUID id, String name, Long chatId, Set<UUID> students,
                               Set<UUID> schedules) implements Serializable {

}
