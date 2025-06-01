package org.ademun.mining_scheduler.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.ademun.mining_scheduler.entity.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class SubjectRepositoryTest {

  @Autowired
  private SubjectRepository subjectRepository;

  @Test
  public void findByName_ExistingName_ReturnsSubject() {
    Subject subject = new Subject();
    subject.setName("Test");
    subjectRepository.save(subject);

    Optional<Subject> found = subjectRepository.findByName(subject.getName());

    assertTrue(found.isPresent());
    assertEquals(subject.getName(), found.get().getName());
  }

  @Test
  public void findByName_NonExistingName_ReturnsEmpty() {
    Subject subject = new Subject();
    subject.setName("Test");
    subjectRepository.save(subject);

    Optional<Subject> found = subjectRepository.findByName("Invalid");

    assertTrue(found.isEmpty());
  }

  @Test
  public void findByName_MixedName_ReturnsSubject() {
    Subject subject = new Subject();
    subject.setName("Test");
    subjectRepository.save(subject);

    Optional<Subject> found = subjectRepository.findByName("T e ST");

    assertTrue(found.isPresent());
    assertEquals(subject.getName(), found.get().getName());
  }

  @Test
  public void isPresent_ExistingName_ReturnsTrue() {
    Subject subject = new Subject();
    subject.setName("Test");
    subjectRepository.save(subject);

    boolean found = subjectRepository.isPresent(subject.getName());

    assertTrue(found);
  }

  @Test
  public void isPresent_NonExistingName_ReturnsFalse() {
    Subject subject = new Subject();
    subject.setName("Test");
    subjectRepository.save(subject);

    boolean found = subjectRepository.isPresent("Invalid");

    assertFalse(found);
  }
}
