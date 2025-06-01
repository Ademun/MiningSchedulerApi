package org.ademun.mining_scheduler.service;

import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Subject;
import org.ademun.mining_scheduler.entity.Teacher;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;

public interface TeacherService {

  Teacher create(Teacher teacher);

  Teacher findById(UUID id) throws ResourceNotFoundException;

  Teacher findByFullName(String fullName) throws ResourceNotFoundException;

  List<Teacher> findAll();

  void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException;

  Subject getSubject(UUID id) throws ResourceNotFoundException;

}
