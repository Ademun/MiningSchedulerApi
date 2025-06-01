package org.ademun.mining_scheduler.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class StudentRepositoryTest {

  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private EntityManager entityManager;

  @Test
  public void findByFullName_ExistingFullName_ReturnsStudent() {
    Student student = new Student();
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");
    Group group = new Group();
    group.setName("Test");
    group.setChatId(1L);
    student.setGroup(group);
    entityManager.persist(group);
    studentRepository.save(student);

    Optional<Student> found = studentRepository.findByFullName(student.getName(),
        student.getSurname(), student.getPatronymic());

    assertTrue(found.isPresent());
    assertEquals(student.getName(), found.get().getName());
  }

  @Test
  public void findByName_NonExistingName_ReturnsEmpty() {
    Student student = new Student();
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");
    Group group = new Group();
    group.setName("Test");
    group.setChatId(1L);
    student.setGroup(group);
    entityManager.persist(group);
    studentRepository.save(student);

    Optional<Student> found = studentRepository.findByFullName("Invalid",
        "Invalid2", "Invalid3");

    assertTrue(found.isEmpty());
  }

  @Test
  public void findByName_MixedName_ReturnsStudent() {
    Student student = new Student();
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");
    Group group = new Group();
    group.setName("Test");
    group.setChatId(1L);
    student.setGroup(group);
    entityManager.persist(group);
    studentRepository.save(student);

    Optional<Student> found = studentRepository.findByFullName("TEsT",
        "Test2", "TESt3");

    assertTrue(found.isPresent());
    assertEquals(student.getName(), found.get().getName());
  }
}
