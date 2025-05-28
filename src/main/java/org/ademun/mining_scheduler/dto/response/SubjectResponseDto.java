package org.ademun.mining_scheduler.dto.response;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.ademun.mining_scheduler.entity.Subject}
 */
public record SubjectResponseDto(UUID id, String name, Set<UUID> teachers) implements
    Serializable {

}
