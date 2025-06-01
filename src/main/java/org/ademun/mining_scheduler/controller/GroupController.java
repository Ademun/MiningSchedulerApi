package org.ademun.mining_scheduler.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.dto.mapper.GroupMapper;
import org.ademun.mining_scheduler.dto.mapper.ScheduleMapper;
import org.ademun.mining_scheduler.dto.mapper.StudentMapper;
import org.ademun.mining_scheduler.dto.request.GroupRequestDto;
import org.ademun.mining_scheduler.dto.request.ScheduleRequestDto;
import org.ademun.mining_scheduler.dto.request.StudentRequestDto;
import org.ademun.mining_scheduler.dto.response.GroupResponseDto;
import org.ademun.mining_scheduler.dto.response.ScheduleResponseDto;
import org.ademun.mining_scheduler.dto.response.StudentResponseDto;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.entity.Student;
import org.ademun.mining_scheduler.service.GroupService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupController {

  private final GroupService groupService;
  private final GroupMapper groupMapper;
  private final StudentMapper studentMapper;
  private final ScheduleMapper scheduleMapper;

  @PostMapping("/groups")
  public HttpEntity<GroupResponseDto> createGroup(@RequestBody GroupRequestDto requestDto) {
    Group group = groupMapper.fromRequest(requestDto);
    Group created = groupService.create(group);
    return new ResponseEntity<>(groupMapper.toResponse(created), HttpStatus.CREATED);
  }

  @RequestMapping("/groups/{id}")
  public HttpEntity<GroupResponseDto> getGroupById(@PathVariable UUID id) {
    Group group = groupService.findById(id);
    return new ResponseEntity<>(groupMapper.toResponse(group), HttpStatus.OK);
  }

  @RequestMapping(value = "/groups", params = "name")
  public HttpEntity<GroupResponseDto> getGroupByName(@RequestParam String name) {
    Group group = groupService.findByName(name);
    return new ResponseEntity<>(groupMapper.toResponse(group), HttpStatus.OK);
  }

  @RequestMapping(value = "/groups", params = "chatId")
  public HttpEntity<GroupResponseDto> getGroupByName(@RequestParam Long chatId) {
    Group group = groupService.findByChatId(chatId);
    return new ResponseEntity<>(groupMapper.toResponse(group), HttpStatus.OK);
  }

  @RequestMapping("/groups")
  public HttpEntity<List<GroupResponseDto>> getAllGroups() {
    List<Group> groups = groupService.findAll();
    return new ResponseEntity<>(groups.stream().map(groupMapper::toResponse).toList(),
        HttpStatus.OK);
  }

  @DeleteMapping("/groups/{id}")
  public HttpEntity<Void> deleteGroup(@PathVariable UUID id) {
    groupService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping("/groups/{id}/students")
  public HttpEntity<List<StudentResponseDto>> getStudents(@PathVariable UUID id) {
    List<Student> students = groupService.getStudents(id);
    return new ResponseEntity<>(students.stream().map(studentMapper::toResponse).toList(),
        HttpStatus.OK);
  }

  @PatchMapping("/groups/{id}/students")
  public HttpEntity<Void> addStudent(@PathVariable UUID id,
      @RequestBody StudentRequestDto student) {
    groupService.addStudent(id, studentMapper.fromRequest(student));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/groups/{id}/students/{studentId}")
  public HttpEntity<Void> removeStudent(@PathVariable UUID id, @PathVariable UUID studentId) {
    groupService.removeStudent(id, studentId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping("/groups/{id}/schedules")
  public HttpEntity<List<ScheduleResponseDto>> getSchedules(@PathVariable UUID id) {
    List<Schedule> schedules = groupService.getSchedules(id);
    return new ResponseEntity<>(schedules.stream().map(scheduleMapper::toResponse).toList(),
        HttpStatus.OK);
  }

  @RequestMapping(value = "/groups/{id}/schedules", params = "week")
  public HttpEntity<ScheduleResponseDto> getScheduleByWeek(@PathVariable UUID id,
      @RequestParam short week) {
    Schedule schedule = groupService.getScheduleByWeek(id, week);
    return new ResponseEntity<>(scheduleMapper.toResponse(schedule), HttpStatus.OK);
  }

  @PatchMapping("/groups/{id}/schedules")
  public HttpEntity<Void> addSchedule(@PathVariable UUID id,
      @RequestBody ScheduleRequestDto schedule) {
    groupService.addSchedule(id, scheduleMapper.fromRequest(schedule));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/groups/{id}/schedules/{scheduleId}")
  public HttpEntity<Void> removeSchedule(@PathVariable UUID id, @PathVariable UUID scheduleId) {
    groupService.removeSchedule(id, scheduleId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
