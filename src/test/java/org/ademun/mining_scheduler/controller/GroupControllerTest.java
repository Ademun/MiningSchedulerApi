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
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@WebMvcTest(controllers = GroupController.class)
@EnableWebMvc
public class GroupControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockitoBean
  private GroupService groupService;
  @MockitoBean
  private GroupMapper groupMapper;
  @MockitoBean
  private StudentMapper studentMapper;
  @MockitoBean
  private ScheduleMapper scheduleMapper;

  @Test
  public void create_NewGroup_ReturnsGroup() throws Exception {
    GroupRequestDto requestDto = new GroupRequestDto("Test", 1L);
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName(requestDto.name());
    group.setChatId(requestDto.chatId());
    GroupResponseDto responseDto = new GroupResponseDto(group.getId(), group.getName(),
        group.getChatId(), new HashSet<>(), new HashSet<>());

    when(groupMapper.fromRequest(requestDto)).thenReturn(group);
    when(groupService.create(group)).thenReturn(group);
    when(groupMapper.toResponse(group)).thenReturn(responseDto);

    mockMvc.perform(post("/groups").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))).andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(group.getId().toString()));
  }

  @Test
  public void findById_ExistingGroup_ReturnsGroups() throws Exception {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");
    group.setChatId(1L);
    GroupResponseDto responseDto = new GroupResponseDto(group.getId(), group.getName(),
        group.getChatId(), new HashSet<>(), new HashSet<>());

    when(groupService.findById(group.getId())).thenReturn(group);
    when(groupMapper.toResponse(group)).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/groups/%s", group.getId()))).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(group.getId().toString()));
  }

  @Test
  public void findById_NonExistingGroup_ReturnsBadRequest() throws Exception {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");
    group.setChatId(1L);

    when(groupService.findById(group.getId())).thenThrow(
        new ResourceNotFoundException("No such group"));

    mockMvc.perform(get(String.format("/groups/%s", group.getId())))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void findByName_ExistingGroup_ReturnsGroups() throws Exception {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");
    group.setChatId(1L);
    GroupResponseDto responseDto = new GroupResponseDto(group.getId(), group.getName(),
        group.getChatId(), new HashSet<>(), new HashSet<>());

    when(groupService.findByName(group.getName())).thenReturn(group);
    when(groupMapper.toResponse(group)).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/groups?name=%s", group.getName())))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(group.getId().toString()));
  }

  @Test
  public void findByName_NonExistingGroup_ReturnsBadRequest() throws Exception {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");
    group.setChatId(1L);

    when(groupService.findByName(group.getName())).thenThrow(
        new ResourceNotFoundException("No such group"));

    mockMvc.perform(get(String.format("/groups?name=%s", group.getName())))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void findByChatId_ExistingGroup_ReturnsGroups() throws Exception {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");
    group.setChatId(1L);
    GroupResponseDto responseDto = new GroupResponseDto(group.getId(), group.getName(),
        group.getChatId(), new HashSet<>(), new HashSet<>());

    when(groupService.findByChatId(group.getChatId())).thenReturn(group);
    when(groupMapper.toResponse(group)).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/groups?chatId=%s", group.getChatId())))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(group.getId().toString()));
  }

  @Test
  public void findByChatId_NonExistingGroup_ReturnsBadRequest() throws Exception {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");
    group.setChatId(1L);

    when(groupService.findByChatId(group.getChatId())).thenThrow(
        new ResourceNotFoundException("No such group"));

    mockMvc.perform(get(String.format("/groups?chatId=%s", group.getChatId())))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getAll_TwoGroups_ReturnsTwoGroups() throws Exception {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");
    group.setChatId(1L);
    GroupResponseDto responseDto = new GroupResponseDto(group.getId(), group.getName(),
        group.getChatId(), new HashSet<>(), new HashSet<>());

    when(groupService.findAll()).thenReturn(List.of(group, group));
    when(groupMapper.toResponse(any(Group.class))).thenReturn(responseDto);

    mockMvc.perform(get("/groups")).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  public void delete_ExistingGroup_ReturnsNoContent() throws Exception {
    UUID groupId = UUID.randomUUID();

    doNothing().when(groupService).delete(groupId);

    mockMvc.perform(delete(String.format("/groups/%s", groupId))).andExpect(status().isNoContent());
  }

  @Test
  public void getStudents_GroupWithTwoStudents_ReturnsTwoStudents() throws Exception {
    UUID groupId = UUID.randomUUID();
    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");
    StudentResponseDto responseDto = new StudentResponseDto(student.getId(), student.getName(),
        student.getSurname(), student.getPatronymic(), null);

    when(groupService.getStudents(groupId)).thenReturn(List.of(student, student));
    when(studentMapper.toResponse(any(Student.class))).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/groups/%s/students", groupId))).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  public void addStudent_ExistingGroup_ReturnsNoContent() throws Exception {
    UUID groupId = UUID.randomUUID();
    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");
    StudentRequestDto requestDto = new StudentRequestDto(student.getName(), student.getSurname(),
        student.getPatronymic());

    when(studentMapper.fromRequest(requestDto)).thenReturn(student);
    doNothing().when(groupService).addStudent(groupId, student);

    mockMvc.perform(patch(String.format("/groups/%s/students", groupId)).content(
            objectMapper.writeValueAsString(requestDto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  public void remove_ExistingStudent_ReturnsNoContent() throws Exception {
    UUID groupId = UUID.randomUUID();
    UUID studentId = UUID.randomUUID();

    doNothing().when(groupService).removeStudent(groupId, studentId);

    mockMvc.perform(delete(String.format("/groups/%s/students/%s", groupId, studentId)))
        .andExpect(status().isNoContent());
  }

  @Test
  public void getSchedules_GroupWithTwoSchedules_ReturnsTwoSchedules() throws Exception {
    UUID groupId = UUID.randomUUID();
    Schedule schedule = new Schedule();
    schedule.setId(UUID.randomUUID());
    schedule.setWeek((short) 1);
    ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule.getId(), schedule.getWeek(),
        null, new HashSet<>());

    when(groupService.getSchedules(groupId)).thenReturn(List.of(schedule, schedule));
    when(scheduleMapper.toResponse(any(Schedule.class))).thenReturn(responseDto);

    mockMvc.perform(get(String.format("/groups/%s/schedules", groupId))).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  public void addSchedule_ExistingGroup_ReturnsNoContent() throws Exception {
    UUID groupId = UUID.randomUUID();
    Schedule schedule = new Schedule();
    schedule.setId(UUID.randomUUID());
    schedule.setWeek((short) 1);
    ScheduleRequestDto requestDto = new ScheduleRequestDto(schedule.getWeek());

    when(scheduleMapper.fromRequest(requestDto)).thenReturn(schedule);
    doNothing().when(groupService).addSchedule(groupId, schedule);

    mockMvc.perform(patch(String.format("/groups/%s/schedules", groupId)).content(
            objectMapper.writeValueAsString(requestDto)).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  public void remove_ExistingSchedule_ReturnsNoContent() throws Exception {
    UUID groupId = UUID.randomUUID();
    UUID scheduleId = UUID.randomUUID();

    doNothing().when(groupService).removeSchedule(groupId, scheduleId);

    mockMvc.perform(delete(String.format("/groups/%s/schedules/%s", groupId, scheduleId)))
        .andExpect(status().isNoContent());
  }
}
