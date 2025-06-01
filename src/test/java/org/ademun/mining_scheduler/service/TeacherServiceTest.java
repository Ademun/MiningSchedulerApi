package org.ademun.mining_scheduler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.ademun.mining_scheduler.entity.Teacher;
import org.ademun.mining_scheduler.repository.TeacherRepository;
import org.ademun.mining_scheduler.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

  @InjectMocks
  private TeacherServiceImpl teacherService;
  @Mock
  private TeacherRepository teacherRepository;

  @Test
  public void create_NewTeacher_ReturnsTeacher() {
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID());
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");

    when(teacherRepository.save(teacher)).thenReturn(teacher);

    Teacher created = teacherService.create(teacher);
    assertNotNull(created.getId());
    assertEquals(teacher.getId(), created.getId());
  }
}
