package org.ademun.mining_scheduler.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link org.ademun.mining_scheduler.entity.Subject}
 */
public record SubjectRequestDto(@Size(message = "Name should be in range of 2-256 symbols", min = 2, max = 256) @NotBlank(message = "Name cannot be empty") String name) implements
    Serializable {
  }
