package org.ademun.mining_scheduler.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import org.ademun.mining_scheduler.entity.Group;

/**
 * DTO for {@link Group}
 */
public record GroupRequestDto(
    @Pattern(regexp = "") @NotBlank @Size(message = "Name must be 8 symbols (XX-XX-XX)", min = 8, max = 8) String name,
    Long chatId) implements Serializable {

}
