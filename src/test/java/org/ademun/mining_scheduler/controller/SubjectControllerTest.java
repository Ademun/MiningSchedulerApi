package org.ademun.mining_scheduler.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.dto.mapper.SubjectMapper;
import org.ademun.mining_scheduler.dto.mapper.TeacherMapper;
import org.ademun.mining_scheduler.dto.request.SubjectRequestDto;
import org.ademun.mining_scheduler.dto.request.TeacherRequestDto;
import org.ademun.mining_scheduler.dto.response.SubjectResponseDto;
import org.ademun.mining_scheduler.dto.response.TeacherResponseDto;
import org.ademun.mining_scheduler.entity.Subject;
import org.ademun.mining_scheduler.entity.Teacher;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@WebMvcTest(controllers = SubjectController.class)
@EnableWebMvc
public class SubjectControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockitoBean
  private SubjectService subjectService;
  @MockitoBean
  private SubjectMapper subjectMapper;
  @MockitoBean
  private TeacherMapper teacherMapper;

  @Test
  public void create_NewSubject_ReturnsSubject() throws Exception {
    SubjectRequestDto requestDto = new SubjectRequestDto("Test");
    Subject subject = new Subject();
    subject.setId(UUID.randomUUID());
    subject.setName(requestDto.name());
    SubjectResponseDto responseDto = new SubjectResponseDto(subject.getId(), subject.getName(),
        new HashSet<>());

    when(subjectMapper.fromRequest(requestDto)).thenReturn(subject);
    when(subjectService.create(subject)).thenReturn(subject);
    when(subjectMapper.toResponse(subject)).thenReturn(responseDto);

    mockMvc.perform(post("/subjects").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))).andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(subject.getId().toString()));
  }

  @Test
  public void findById_ExistingSubject_ReturnsSubjects() throws Exception {
    Subject subject = new Subject();
    subject.setId(UUID.randomUUID());
    subject.setName("Test");
    SubjectResponseDto responseDto = new SubjectResponseDto(subject.getId(), subject.getName(),
        new HashSet<>());

    when(subjectService.findById(subject.getId())).thenReturn(subject);
    when(subjectMapper.toResponse(subject)).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/subjects/%s", subject.getId()))).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(subject.getId().toString()));
  }

  @Test
  public void findById_NonExistingSubject_ReturnsBadRequest() throws Exception {
    Subject subject = new Subject();
    subject.setId(UUID.randomUUID());
    subject.setName("Test");

    when(subjectService.findById(subject.getId())).thenThrow(
        new ResourceNotFoundException("No such subject"));

    mockMvc.perform(get(String.format("/subjects/%s", subject.getId())))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void findByName_ExistingSubject_ReturnsSubjects() throws Exception {
    Subject subject = new Subject();
    subject.setId(UUID.randomUUID());
    subject.setName("Test");
    SubjectResponseDto responseDto = new SubjectResponseDto(subject.getId(), subject.getName(),
        new HashSet<>());

    when(subjectService.findByName(subject.getName())).thenReturn(subject);
    when(subjectMapper.toResponse(subject)).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/subjects?name=%s", subject.getName())))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(subject.getId().toString()));
  }

  @Test
  public void findByName_NonExistingSubject_ReturnsBadRequest() throws Exception {
    Subject subject = new Subject();
    subject.setId(UUID.randomUUID());
    subject.setName("Test");

    when(subjectService.findByName(subject.getName())).thenThrow(
        new ResourceNotFoundException("No such subject"));

    mockMvc.perform(get(String.format("/subjects?name=%s", subject.getName())))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getAll_TwoSubjects_ReturnsTwoSubjects() throws Exception {
    Subject subject = new Subject();
    subject.setId(UUID.randomUUID());
    subject.setName("Test");
    SubjectResponseDto responseDto = new SubjectResponseDto(subject.getId(), subject.getName(),
        new HashSet<>());

    when(subjectService.findAll()).thenReturn(List.of(subject, subject));
    when(subjectMapper.toResponse(any(Subject.class))).thenReturn(responseDto);

    mockMvc.perform(get("/subjects")).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  public void delete_ExistingSubject_ReturnsNoContent() throws Exception {
    UUID subjectId = UUID.randomUUID();

    doNothing().when(subjectService).delete(subjectId);

    mockMvc.perform(delete(String.format("/subjects/%s", subjectId)))
        .andExpect(status().isNoContent());
  }

  @Test
  public void getTeachers_SubjectWithTwoTeachers_ReturnsTwoTeachers() throws Exception {
    UUID subjectId = UUID.randomUUID();
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID());
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");
    TeacherResponseDto responseDto = new TeacherResponseDto(teacher.getId(), teacher.getName(),
        teacher.getSurname(), teacher.getPatronymic(), null);

    when(subjectService.getTeachers(subjectId)).thenReturn(List.of(teacher, teacher));
    when(teacherMapper.toResponse(any(Teacher.class))).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/subjects/%s/teachers", subjectId)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  public void addTeacher_ExistingSubject_ReturnsNoContent() throws Exception {
    UUID subjectId = UUID.randomUUID();
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID());
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");
    TeacherRequestDto requestDto = new TeacherRequestDto(teacher.getName(), teacher.getSurname(),
        teacher.getPatronymic());

    when(teacherMapper.fromRequest(requestDto)).thenReturn(teacher);
    doNothing().when(subjectService).addTeacher(subjectId, teacher);

    mockMvc.perform(patch(String.format("/subjects/%s/teachers", subjectId)).content(
            objectMapper.writeValueAsString(requestDto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  public void remove_ExistingTeacher_ReturnsNoContent() throws Exception {
    UUID subjectId = UUID.randomUUID();
    UUID teacherId = UUID.randomUUID();

    doNothing().when(subjectService).removeTeacher(subjectId, teacherId);

    mockMvc.perform(delete(String.format("/subjects/%s/teachers/%s", subjectId, teacherId)))
        .andExpect(status().isNoContent());
  }
}
