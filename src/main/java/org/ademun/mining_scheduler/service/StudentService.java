package org.ademun.mining_scheduler.service;

import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Student;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;

public interface StudentService {

  Student create(Student student);

  Student findById(UUID id) throws ResourceNotFoundException;

  Student findByFullName(String fullName) throws ResourceNotFoundException;

  List<Student> findAll();

  void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException;

  Group getGroup(UUID id) throws ResourceNotFoundException;

}
