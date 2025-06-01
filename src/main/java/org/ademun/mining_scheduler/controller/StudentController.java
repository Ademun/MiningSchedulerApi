package org.ademun.mining_scheduler.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.dto.mapper.GroupMapper;
import org.ademun.mining_scheduler.dto.mapper.StudentMapper;
import org.ademun.mining_scheduler.dto.request.StudentRequestDto;
import org.ademun.mining_scheduler.dto.response.GroupResponseDto;
import org.ademun.mining_scheduler.dto.response.StudentResponseDto;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Student;
import org.ademun.mining_scheduler.service.StudentService;
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
public class StudentController {

  private final StudentService studentService;
  private final StudentMapper studentMapper;
  private final GroupMapper groupMapper;

  @PostMapping("/students")
  public HttpEntity<StudentResponseDto> createStudent(@RequestBody StudentRequestDto requestDto) {
    Student student = studentMapper.fromRequest(requestDto);
    Student created = studentService.create(student);
    return new ResponseEntity<>(studentMapper.toResponse(created), HttpStatus.CREATED);
  }

  @RequestMapping("/students/{id}")
  public HttpEntity<StudentResponseDto> getStudentById(@PathVariable UUID id) {
    Student student = studentService.findById(id);
    return new ResponseEntity<>(studentMapper.toResponse(student), HttpStatus.OK);
  }

  @RequestMapping(value = "/students", params = "fullName")
  public HttpEntity<StudentResponseDto> getStudentByName(@RequestParam String fullName) {
    Student student = studentService.findByFullName(fullName);
    return new ResponseEntity<>(studentMapper.toResponse(student), HttpStatus.OK);
  }

  @RequestMapping("/students")
  public HttpEntity<List<StudentResponseDto>> getAllStudents() {
    List<Student> students = studentService.findAll();
    return new ResponseEntity<>(students.stream().map(studentMapper::toResponse).toList(),
        HttpStatus.OK);
  }

  @DeleteMapping("/students/{id}")
  public HttpEntity<Void> deleteStudent(@PathVariable UUID id) {
    studentService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping("/students/{id}/group")
  public HttpEntity<GroupResponseDto> getGroup(@PathVariable UUID id) {
    Group group = studentService.getGroup(id);
    return new ResponseEntity<>(groupMapper.toResponse(group), HttpStatus.OK);
  }
}
