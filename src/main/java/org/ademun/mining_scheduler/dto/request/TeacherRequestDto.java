package org.ademun.mining_scheduler.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link org.ademun.mining_scheduler.entity.Teacher}
 */
public record TeacherRequestDto(
    @Size(message = "Name must be in range of 2-64 symbols", min = 2, max = 64) @NotBlank(message = "Name cannot be empty") String name,
    @Size(message = "Surname must be in range of 2-64 symbols", min = 2, max = 64) @NotBlank(message = "Surname cannot be empty") String surname,
    @Size(message = "Patronymic must be in range of 2-64 symbols", min = 2, max = 64) @NotBlank(message = "Patronymic cannot be empty") String patronymic) implements
    Serializable {

}
