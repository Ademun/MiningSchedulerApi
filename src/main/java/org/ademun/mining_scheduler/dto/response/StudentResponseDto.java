package org.ademun.mining_scheduler.dto.response;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.ademun.mining_scheduler.entity.Student}
 */
public record StudentResponseDto(UUID id, String name, String surname, String patronymic,
                                 UUID group) implements
    Serializable {

}
