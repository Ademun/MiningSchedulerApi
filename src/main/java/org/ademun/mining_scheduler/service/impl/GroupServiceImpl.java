package org.ademun.mining_scheduler.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.entity.Student;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.repository.GroupRepository;
import org.ademun.mining_scheduler.service.GroupService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;

  @Override
  public Group create(Group group) throws ResourceAlreadyExistsException {
    if (groupRepository.isPresent(group.getName())) {
      throw new ResourceAlreadyExistsException("Group already exists");
    }
    return groupRepository.save(group);
  }

  @Override
  public Group findById(UUID id) throws ResourceNotFoundException {
    return groupRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No such group"));
  }

  @Override
  public Group findByName(String groupName) throws ResourceNotFoundException {
    return groupRepository.findByName(groupName)
        .orElseThrow(() -> new ResourceNotFoundException("No such group"));
  }

  @Override
  public Group findByChatId(Long chatId) throws ResourceNotFoundException {
    return groupRepository.findByChatId(chatId)
        .orElseThrow(() -> new ResourceNotFoundException("No such group"));
  }

  @Override
  public List<Group> findAll() {
    return groupRepository.findAll();
  }

  @Override
  public void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException {
    Group group = findById(id);
    if (!group.getStudents().isEmpty()) {
      throw new ResourceIsBeingUsedException("Cant delete a group with students");
    }
    groupRepository.delete(group);
  }

  @Override
  public List<Student> getStudents(UUID id) throws ResourceNotFoundException {
    return findById(id).getStudents().stream().toList();
  }

  @Override
  @Transactional
  public void addStudent(UUID id, Student student)
      throws ResourceNotFoundException, ResourceAlreadyExistsException {
    Group group = findById(id);
    group.getStudents().add(student);
    student.setGroup(group);
    groupRepository.save(group);
  }

  @Override
  @Transactional
  public void removeStudent(UUID id, UUID studentId) throws ResourceNotFoundException {
    Group group = findById(id);
    Student remove = group.getStudents().stream()
        .filter(student -> student.getId().equals(studentId)).findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("No such student"));
    group.getStudents().remove(remove);
    remove.setGroup(null);
    groupRepository.save(group);
  }

  @Override
  public List<Schedule> getSchedules(UUID id) throws ResourceNotFoundException {
    return findById(id).getSchedules().stream().toList();
  }

  @Override
  public Schedule getScheduleByWeek(UUID id, short week) throws ResourceNotFoundException {
    return getSchedules(id).stream().filter(schedule -> schedule.getWeek() == week).findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("No such schedule"));
  }

  @Override
  @Transactional
  public void addSchedule(UUID id, Schedule schedule) throws ResourceNotFoundException {
    Group group = findById(id);
    group.getSchedules().add(schedule);
    schedule.setGroup(group);
    groupRepository.save(group);
  }

  @Override
  @Transactional
  public void removeSchedule(UUID id, UUID scheduleId) throws ResourceNotFoundException {
    Group group = findById(id);
    Schedule remove = group.getSchedules().stream()
        .filter(schedule -> schedule.getId().equals(scheduleId)).findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("No such schedule"));
    group.getSchedules().remove(remove);
    remove.setGroup(null);
    groupRepository.save(group);
  }
}
