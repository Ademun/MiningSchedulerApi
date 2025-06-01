package org.ademun.mining_scheduler.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.dto.mapper.SubjectMapper;
import org.ademun.mining_scheduler.dto.mapper.TeacherMapper;
import org.ademun.mining_scheduler.dto.request.TeacherRequestDto;
import org.ademun.mining_scheduler.dto.response.SubjectResponseDto;
import org.ademun.mining_scheduler.dto.response.TeacherResponseDto;
import org.ademun.mining_scheduler.entity.Subject;
import org.ademun.mining_scheduler.entity.Teacher;
import org.ademun.mining_scheduler.service.TeacherService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;
  private final TeacherMapper teacherMapper;
  private final SubjectMapper subjectMapper;

  @PostMapping("/teachers")
  public HttpEntity<TeacherResponseDto> createTeacher(@RequestBody TeacherRequestDto requestDto) {
    Teacher teacher = teacherMapper.fromRequest(requestDto);
    Teacher created = teacherService.create(teacher);
    return new ResponseEntity<>(teacherMapper.toResponse(created), HttpStatus.CREATED);
  }

  @RequestMapping("/teachers/{id}")
  public HttpEntity<TeacherResponseDto> getTeacherById(@PathVariable UUID id) {
    Teacher teacher = teacherService.findById(id);
    return new ResponseEntity<>(teacherMapper.toResponse(teacher), HttpStatus.OK);
  }

  @RequestMapping(value = "/teachers", params = "fullName")
  public HttpEntity<TeacherResponseDto> getTeacherByName(@RequestParam String fullName) {
    Teacher teacher = teacherService.findByFullName(fullName);
    return new ResponseEntity<>(teacherMapper.toResponse(teacher), HttpStatus.OK);
  }

  @RequestMapping("/teachers")
  public HttpEntity<List<TeacherResponseDto>> getAllTeachers() {
    List<Teacher> teachers = teacherService.findAll();
    return new ResponseEntity<>(teachers.stream().map(teacherMapper::toResponse).toList(),
        HttpStatus.OK);
  }

  @DeleteMapping("/teachers/{id}")
  public HttpEntity<Void> deleteTeacher(@PathVariable UUID id) {
    teacherService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping("/teachers/{id}/subject")
  public HttpEntity<SubjectResponseDto> getSubject(@PathVariable UUID id) {
    Subject subject = teacherService.getSubject(id);
    return new ResponseEntity<>(subjectMapper.toResponse(subject), HttpStatus.OK);
  }
}
