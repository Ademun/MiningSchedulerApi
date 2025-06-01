package org.ademun.mining_scheduler.dto.mapper;

import org.ademun.mining_scheduler.dto.request.StudentRequestDto;
import org.ademun.mining_scheduler.dto.response.StudentResponseDto;
import org.ademun.mining_scheduler.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

  public Student fromRequest(StudentRequestDto requestDto) {
    Student student = new Student();
    student.setName(requestDto.name());
    student.setSurname(requestDto.surname());
    student.setPatronymic(requestDto.patronymic());
    return student;
  }

  public StudentResponseDto toResponse(Student student) {
    return new StudentResponseDto(student.getId(), student.getName(), student.getSurname(),
        student.getPatronymic(), student.getGroup().getId());
  }
}
