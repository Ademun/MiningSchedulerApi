package org.ademun.mining_scheduler.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.dto.mapper.SubjectMapper;
import org.ademun.mining_scheduler.dto.mapper.TeacherMapper;
import org.ademun.mining_scheduler.dto.request.TeacherRequestDto;
import org.ademun.mining_scheduler.dto.response.SubjectResponseDto;
import org.ademun.mining_scheduler.dto.response.TeacherResponseDto;
import org.ademun.mining_scheduler.entity.Subject;
import org.ademun.mining_scheduler.entity.Teacher;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@WebMvcTest(controllers = TeacherController.class)
@EnableWebMvc
public class TeacherControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockitoBean
  private TeacherService teacherService;
  @MockitoBean
  private TeacherMapper teacherMapper;
  @MockitoBean
  private SubjectMapper subjectMapper;

  @Test
  public void create_NewTeacher_ReturnsTeacher() throws Exception {
    TeacherRequestDto requestDto = new TeacherRequestDto("Test", "Test2", "Test3");
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID());
    teacher.setName(requestDto.name());
    teacher.setSurname(requestDto.surname());
    teacher.setPatronymic(requestDto.patronymic());
    TeacherResponseDto responseDto = new TeacherResponseDto(teacher.getId(), teacher.getName(),
        teacher.getSurname(), teacher.getPatronymic(), null);

    when(teacherMapper.fromRequest(requestDto)).thenReturn(teacher);
    when(teacherService.create(teacher)).thenReturn(teacher);
    when(teacherMapper.toResponse(teacher)).thenReturn(responseDto);

    mockMvc.perform(post("/teachers").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))).andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(teacher.getId().toString()));
  }

  @Test
  public void findById_ExistingTeacher_ReturnsTeachers() throws Exception {
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID());
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");
    TeacherResponseDto responseDto = new TeacherResponseDto(teacher.getId(), teacher.getName(),
        teacher.getSurname(), teacher.getPatronymic(), null);

    when(teacherService.findById(teacher.getId())).thenReturn(teacher);
    when(teacherMapper.toResponse(teacher)).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/teachers/%s", teacher.getId()))).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(teacher.getId().toString()));
  }

  @Test
  public void findById_NonExistingTeacher_ReturnsBadRequest() throws Exception {
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID());
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");

    when(teacherService.findById(teacher.getId())).thenThrow(
        new ResourceNotFoundException("No such teacher"));

    mockMvc.perform(get(String.format("/teachers/%s", teacher.getId())))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void findByName_ExistingTeacher_ReturnsTeachers() throws Exception {
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID());
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");
    TeacherResponseDto responseDto = new TeacherResponseDto(teacher.getId(), teacher.getName(),
        teacher.getSurname(), teacher.getPatronymic(), null);

    when(teacherService.findByFullName(teacher.getFullName())).thenReturn(teacher);
    when(teacherMapper.toResponse(teacher)).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/teachers?fullName=%s", teacher.getFullName())))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(teacher.getId().toString()));
  }

  @Test
  public void findByName_NonExistingTeacher_ReturnsBadRequest() throws Exception {
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID());
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");

    when(teacherService.findByFullName(teacher.getFullName())).thenThrow(
        new ResourceNotFoundException("No such teacher"));

    mockMvc.perform(get(String.format("/teachers?fullName=%s", teacher.getFullName())))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getAll_TwoTeachers_ReturnsTwoTeachers() throws Exception {
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID());
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");
    TeacherResponseDto responseDto = new TeacherResponseDto(teacher.getId(), teacher.getName(),
        teacher.getSurname(), teacher.getPatronymic(), null);

    when(teacherService.findAll()).thenReturn(List.of(teacher, teacher));
    when(teacherMapper.toResponse(any(Teacher.class))).thenReturn(responseDto);

    mockMvc.perform(get("/teachers")).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  public void delete_ExistingTeacher_ReturnsNoContent() throws Exception {
    UUID teacherId = UUID.randomUUID();

    doNothing().when(teacherService).delete(teacherId);

    mockMvc.perform(delete(String.format("/teachers/%s", teacherId)))
        .andExpect(status().isNoContent());
  }

  @Test
  public void getSubject_TeacherWithSubject_ReturnsSubject() throws Exception {
    UUID teacherId = UUID.randomUUID();
    Subject subject = new Subject();
    subject.setId(UUID.randomUUID());
    subject.setName("Test");
    SubjectResponseDto responseDto = new SubjectResponseDto(subject.getId(), subject.getName(),
        null);

    when(teacherService.getSubject(teacherId)).thenReturn(subject);
    when(subjectMapper.toResponse(any(Subject.class))).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/teachers/%s/subject", teacherId)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(subject.getId().toString()));
  }
}
