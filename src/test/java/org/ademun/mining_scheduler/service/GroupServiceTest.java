package org.ademun.mining_scheduler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Student;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.repository.GroupRepository;
import org.ademun.mining_scheduler.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

  @InjectMocks
  private GroupServiceImpl groupService;
  @Mock
  private GroupRepository groupRepository;

  @Test
  public void delete_GroupWithoutStudents_Success() {
    Group group = new Group();
    group.setName("Test");

    when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
    doNothing().when(groupRepository).delete(group);

    groupService.delete(group.getId());
  }

  @Test
  public void delete_GroupWithStudents_ThrowsException() {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");
    Student student = new Student();
    student.setId(UUID.randomUUID());
    student.setName("Test");
    student.setSurname("Test2");
    student.setPatronymic("Test3");
    group.getStudents().add(student);
    student.setGroup(group);

    when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));

    ResourceIsBeingUsedException exception = assertThrows(ResourceIsBeingUsedException.class,
        () -> groupService.delete(group.getId()));
    assertEquals("Cant delete a group with students", exception.getMessage());
  }
}
