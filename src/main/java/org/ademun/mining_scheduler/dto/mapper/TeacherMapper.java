package org.ademun.mining_scheduler.dto.mapper;

import org.ademun.mining_scheduler.dto.request.TeacherRequestDto;
import org.ademun.mining_scheduler.dto.response.TeacherResponseDto;
import org.ademun.mining_scheduler.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

  public Teacher fromRequest(TeacherRequestDto requestDto) {
    Teacher teacher = new Teacher();
    teacher.setName(requestDto.name());
    teacher.setSurname(requestDto.surname());
    teacher.setPatronymic(requestDto.patronymic());
    return teacher;
  }

  public TeacherResponseDto toResponse(Teacher teacher) {
    return new TeacherResponseDto(teacher.getId(), teacher.getName(), teacher.getSurname(),
        teacher.getPatronymic(), teacher.getSubject().getId());
  }
}
