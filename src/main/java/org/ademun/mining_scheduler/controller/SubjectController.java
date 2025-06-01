package org.ademun.mining_scheduler.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.dto.mapper.SubjectMapper;
import org.ademun.mining_scheduler.dto.mapper.TeacherMapper;
import org.ademun.mining_scheduler.dto.request.SubjectRequestDto;
import org.ademun.mining_scheduler.dto.request.TeacherRequestDto;
import org.ademun.mining_scheduler.dto.response.SubjectResponseDto;
import org.ademun.mining_scheduler.dto.response.TeacherResponseDto;
import org.ademun.mining_scheduler.entity.Subject;
import org.ademun.mining_scheduler.entity.Teacher;
import org.ademun.mining_scheduler.service.SubjectService;
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
public class SubjectController {

  private final SubjectService subjectService;
  private final SubjectMapper subjectMapper;
  private final TeacherMapper teacherMapper;

  @PostMapping("/subjects")
  public HttpEntity<SubjectResponseDto> createSubject(@RequestBody SubjectRequestDto requestDto) {
    Subject subject = subjectMapper.fromRequest(requestDto);
    Subject created = subjectService.create(subject);
    return new ResponseEntity<>(subjectMapper.toResponse(created), HttpStatus.CREATED);
  }

  @RequestMapping("/subjects/{id}")
  public HttpEntity<SubjectResponseDto> getSubjectById(@PathVariable UUID id) {
    Subject subject = subjectService.findById(id);
    return new ResponseEntity<>(subjectMapper.toResponse(subject), HttpStatus.OK);
  }

  @RequestMapping(value = "/subjects", params = "name")
  public HttpEntity<SubjectResponseDto> getSubjectByName(@RequestParam String name) {
    Subject subject = subjectService.findByName(name);
    return new ResponseEntity<>(subjectMapper.toResponse(subject), HttpStatus.OK);
  }

  @RequestMapping("/subjects")
  public HttpEntity<List<SubjectResponseDto>> getAllSubjects() {
    List<Subject> subjects = subjectService.findAll();
    return new ResponseEntity<>(subjects.stream().map(subjectMapper::toResponse).toList(),
        HttpStatus.OK);
  }

  @DeleteMapping("/subjects/{id}")
  public HttpEntity<Void> deleteSubject(@PathVariable UUID id) {
    subjectService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping("/subjects/{id}/teachers")
  public HttpEntity<List<TeacherResponseDto>> getTeachers(@PathVariable UUID id) {
    List<Teacher> teachers = subjectService.getTeachers(id);
    return new ResponseEntity<>(teachers.stream().map(teacherMapper::toResponse).toList(),
        HttpStatus.OK);
  }

  @PatchMapping("/subjects/{id}/teachers")
  public HttpEntity<Void> addTeacher(@PathVariable UUID id, @RequestBody TeacherRequestDto teacher) {
    subjectService.addTeacher(id, teacherMapper.fromRequest(teacher));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/subjects/{id}/teachers/{teacherId}")
  public HttpEntity<Void> removeTeacher(@PathVariable UUID id, @PathVariable UUID teacherId) {
    subjectService.removeTeacher(id, teacherId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
