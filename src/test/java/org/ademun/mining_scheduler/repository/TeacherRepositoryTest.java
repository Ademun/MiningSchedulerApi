package org.ademun.mining_scheduler.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.ademun.mining_scheduler.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TeacherRepositoryTest {

  @Autowired
  private TeacherRepository teacherRepository;

  @Test
  public void findByFullName_ExistingFullName_ReturnsTeacher() {
    Teacher teacher = new Teacher();
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");
    teacherRepository.save(teacher);

    Optional<Teacher> found = teacherRepository.findByFullName(teacher.getName(),
        teacher.getSurname(), teacher.getPatronymic());

    assertTrue(found.isPresent());
    assertEquals(teacher.getName(), found.get().getName());
  }

  @Test
  public void findByName_NonExistingName_ReturnsEmpty() {
    Teacher teacher = new Teacher();
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");
    teacherRepository.save(teacher);

    Optional<Teacher> found = teacherRepository.findByFullName("Invalid",
        "Invalid2", "Invalid3");

    assertTrue(found.isEmpty());
  }

  @Test
  public void findByName_MixedName_ReturnsTeacher() {
    Teacher teacher = new Teacher();
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");
    teacherRepository.save(teacher);

    Optional<Teacher> found = teacherRepository.findByFullName("TEsT",
        "Test2", "TESt3");

    assertTrue(found.isPresent());
    assertEquals(teacher.getName(), found.get().getName());
  }
}
