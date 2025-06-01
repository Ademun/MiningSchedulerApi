package org.ademun.mining_scheduler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Subject;
import org.ademun.mining_scheduler.entity.Teacher;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.repository.SubjectRepository;
import org.ademun.mining_scheduler.service.impl.SubjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

  @InjectMocks
  private SubjectServiceImpl subjectService;
  @Mock
  private SubjectRepository subjectRepository;

  @Test
  public void create_NewSubject_ReturnsSubject() {
    Subject subject = new Subject();
    subject.setId(UUID.randomUUID());
    subject.setName("Test");

    when(subjectRepository.isPresent(subject.getName())).thenReturn(false);
    when(subjectRepository.save(subject)).thenReturn(subject);

    Subject created = subjectService.create(subject);
    assertNotNull(created.getId());
    assertEquals(subject.getId(), created.getId());
  }

  @Test
  public void create_ExistingSubject_ThrowsException() {
    Subject subject = new Subject();
    subject.setId(UUID.randomUUID());
    subject.setName("Test");

    when(subjectRepository.isPresent(subject.getName())).thenReturn(true);

    ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
        () -> subjectService.create(subject));
    assertEquals("Subject already exists", exception.getMessage());
  }

  @Test
  public void delete_SubjectWithoutTeachers_Success() {
    Subject subject = new Subject();
    subject.setName("Test");

    when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));
    doNothing().when(subjectRepository).delete(subject);

    subjectService.delete(subject.getId());
  }

  @Test
  public void delete_SubjectWithTeachers_ThrowsException() {
    Subject subject = new Subject();
    subject.setId(UUID.randomUUID());
    subject.setName("Test");
    Teacher teacher = new Teacher();
    teacher.setId(UUID.randomUUID());
    teacher.setName("Test");
    teacher.setSurname("Test2");
    teacher.setPatronymic("Test3");
    subject.getTeachers().add(teacher);
    teacher.setSubject(subject);

    when(subjectRepository.findById(subject.getId())).thenReturn(Optional.of(subject));

    ResourceIsBeingUsedException exception = assertThrows(ResourceIsBeingUsedException.class,
        () -> subjectService.delete(subject.getId()));
    assertEquals("The subject has teachers", exception.getMessage());
  }
}
