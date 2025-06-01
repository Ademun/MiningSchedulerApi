package org.ademun.mining_scheduler.service;

import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.entity.Student;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;

public interface GroupService {

  Group create(Group group) throws ResourceAlreadyExistsException;

  Group findById(UUID id) throws ResourceNotFoundException;

  Group findByName(String groupName) throws ResourceNotFoundException;

  Group findByChatId(Long chatId) throws ResourceNotFoundException;

  List<Group> findAll();

  void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException;

  List<Student> getStudents(UUID id) throws ResourceNotFoundException;

  void addStudent(UUID id, Student student)
      throws ResourceNotFoundException, ResourceAlreadyExistsException;

  void removeStudent(UUID id, UUID studentId)
      throws ResourceNotFoundException;

  List<Schedule> getSchedules(UUID id) throws ResourceNotFoundException;

  Schedule getScheduleByWeek(UUID id, short week) throws ResourceNotFoundException;

  void addSchedule(UUID id, Schedule schedule)
      throws ResourceNotFoundException, ResourceAlreadyExistsException;

  void removeSchedule(UUID id, UUID scheduleId)
      throws ResourceNotFoundException;
}
