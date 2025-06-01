package org.ademun.mining_scheduler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.ademun.mining_scheduler.entity.Student;
import org.ademun.mining_scheduler.repository.StudentRepository;
import org.ademun.mining_scheduler.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

  @InjectMocks
  private StudentServiceImpl studentService;
  @Mock
  private StudentRepository studentRepository;

  @Test
  public void create_NewStudent_ReturnsStudent() {
    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");

    when(studentRepository.save(student)).thenReturn(student);

    Student created = studentService.create(student);
    assertNotNull(created.getId());
    assertEquals(student.getId(), created.getId());
  }
}
