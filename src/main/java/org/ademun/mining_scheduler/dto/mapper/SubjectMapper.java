package org.ademun.mining_scheduler.dto.mapper;

import java.util.stream.Collectors;
import org.ademun.mining_scheduler.dto.request.SubjectRequestDto;
import org.ademun.mining_scheduler.dto.response.SubjectResponseDto;
import org.ademun.mining_scheduler.entity.Subject;
import org.ademun.mining_scheduler.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

  public Subject fromRequest(SubjectRequestDto requestDto) {
    Subject subject = new Subject();
    subject.setName(requestDto.name());
    return subject;
  }

  public SubjectResponseDto toResponse(Subject subject) {
    return new SubjectResponseDto(subject.getId(), subject.getName(),
        subject.getTeachers().stream().map(Teacher::getId).collect(Collectors.toSet()));
  }
}
