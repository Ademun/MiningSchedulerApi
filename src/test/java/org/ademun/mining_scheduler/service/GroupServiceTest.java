package org.ademun.mining_scheduler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.entity.Student;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
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
  public void create_NewGroup_ReturnsGroup() {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");

    when(groupRepository.isPresent(group.getName())).thenReturn(false);
    when(groupRepository.save(group)).thenReturn(group);

    Group created = groupService.create(group);
    assertNotNull(created.getId());
    assertEquals(group.getId(), created.getId());
  }

  @Test
  public void create_ExistingGroup_ThrowsException() {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");

    when(groupRepository.isPresent(group.getName())).thenReturn(true);

    ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,
        () -> groupService.create(group));
    assertEquals("Group already exists", exception.getMessage());
  }

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

  @Test
  public void findScheduleByWeek_GroupWithSchedule_ReturnsSchedule() {
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Test");
    Schedule schedule = new Schedule();
    schedule.setId(UUID.randomUUID());
    schedule.setWeek((short) 2);
    group.getSchedules().add(schedule);

    when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));

    Schedule found = groupService.getScheduleByWeek(group.getId(), (short) 2);

    assertNotNull(found);
    assertEquals(schedule.getId(), found.getId());
  }
}
