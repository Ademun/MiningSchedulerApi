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
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.dto.mapper.GroupMapper;
import org.ademun.mining_scheduler.dto.mapper.StudentMapper;
import org.ademun.mining_scheduler.dto.request.StudentRequestDto;
import org.ademun.mining_scheduler.dto.response.GroupResponseDto;
import org.ademun.mining_scheduler.dto.response.StudentResponseDto;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Student;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@WebMvcTest(controllers = StudentController.class)
@EnableWebMvc
public class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockitoBean
  private StudentService studentService;
  @MockitoBean
  private StudentMapper studentMapper;
  @MockitoBean
  private GroupMapper groupMapper;

  @Test
  public void create_NewStudent_ReturnsStudent() throws Exception {
    StudentRequestDto requestDto = new StudentRequestDto("Test", "Test2", "Test3");
    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setName(requestDto.name());
    student.setSurname(requestDto.surname());
    student.setPatronymic(requestDto.patronymic());
    StudentResponseDto responseDto = new StudentResponseDto(student.getId(), student.getName(),
        student.getSurname(), student.getPatronymic(), null);

    when(studentMapper.fromRequest(requestDto)).thenReturn(student);
    when(studentService.create(student)).thenReturn(student);
    when(studentMapper.toResponse(student)).thenReturn(responseDto);

    mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))).andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(student.getId().toString()));
  }

  @Test
  public void findById_ExistingStudent_ReturnsStudents() throws Exception {
    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");
    StudentResponseDto responseDto = new StudentResponseDto(student.getId(), student.getName(),
        student.getSurname(), student.getPatronymic(), null);

    when(studentService.findById(student.getId())).thenReturn(student);
    when(studentMapper.toResponse(student)).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/students/%s", student.getId()))).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(student.getId().toString()));
  }

  @Test
  public void findById_NonExistingStudent_ReturnsBadRequest() throws Exception {
    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");

    when(studentService.findById(student.getId())).thenThrow(
        new ResourceNotFoundException("No such student"));

    mockMvc.perform(get(String.format("/students/%s", student.getId())))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void findByName_ExistingStudent_ReturnsStudents() throws Exception {
    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");
    StudentResponseDto responseDto = new StudentResponseDto(student.getId(), student.getName(),
        student.getSurname(), student.getPatronymic(), null);

    when(studentService.findByFullName(student.getFullName())).thenReturn(student);
    when(studentMapper.toResponse(student)).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/students?fullName=%s", student.getFullName())))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(student.getId().toString()));
  }

  @Test
  public void findByName_NonExistingStudent_ReturnsBadRequest() throws Exception {
    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");

    when(studentService.findByFullName(student.getFullName())).thenThrow(
        new ResourceNotFoundException("No such student"));

    mockMvc.perform(get(String.format("/students?fullName=%s", student.getFullName())))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getAll_TwoStudents_ReturnsTwoStudents() throws Exception {
    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");
    StudentResponseDto responseDto = new StudentResponseDto(student.getId(), student.getName(),
        student.getSurname(), student.getPatronymic(), null);

    when(studentService.findAll()).thenReturn(List.of(student, student));
    when(studentMapper.toResponse(any(Student.class))).thenReturn(responseDto);

    mockMvc.perform(get("/students")).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  public void delete_ExistingStudent_ReturnsNoContent() throws Exception {
    UUID studentId = UUID.randomUUID();

    doNothing().when(studentService).delete(studentId);

    mockMvc.perform(delete(String.format("/students/%s", studentId)))
        .andExpect(status().isNoContent());
  }

  @Test
  public void getGroup_StudentWithGroup_ReturnsGroup() throws Exception {
    UUID studentId = UUID.randomUUID();
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");
    group.setChatId(1L);
    GroupResponseDto responseDto = new GroupResponseDto(group.getId(), group.getName(),
        group.getChatId(),
        new HashSet<>(), new HashSet<>());

    when(studentService.getGroup(studentId)).thenReturn(group);
    when(groupMapper.toResponse(any(Group.class))).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/students/%s/group", studentId)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(group.getId().toString()));
  }
}
