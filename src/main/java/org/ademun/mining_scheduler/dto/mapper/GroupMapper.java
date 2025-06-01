package org.ademun.mining_scheduler.dto.mapper;

import java.util.stream.Collectors;
import org.ademun.mining_scheduler.dto.request.GroupRequestDto;
import org.ademun.mining_scheduler.dto.response.GroupResponseDto;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

  public Group fromRequest(GroupRequestDto requestDto) {
    Group group = new Group();
    group.setName(requestDto.name());
    group.setChatId(requestDto.chatId());
    return group;
  }

  public GroupResponseDto toResponse(Group group) {
    return new GroupResponseDto(group.getId(), group.getName(), group.getChatId(),
        group.getStudents().stream().map(Student::getId).collect(Collectors.toSet()),
        group.getSchedules().stream().map(Schedule::getId).collect(Collectors.toSet()));
  }
}
