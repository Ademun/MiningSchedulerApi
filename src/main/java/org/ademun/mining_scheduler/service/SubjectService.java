package org.ademun.mining_scheduler.service;

import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Subject;
import org.ademun.mining_scheduler.entity.Teacher;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;

public interface SubjectService {

  Subject create(Subject subject) throws ResourceAlreadyExistsException;

  Subject findById(UUID id) throws ResourceNotFoundException;

  Subject findByName(String subjectName) throws ResourceNotFoundException;

  List<Subject> findAll();

  void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException;

  List<Teacher> getTeachers(UUID id) throws ResourceNotFoundException;

  void addTeacher(UUID id, Teacher teacher)
      throws ResourceNotFoundException, ResourceAlreadyExistsException;

  void removeTeacher(UUID id, UUID teacherId) throws ResourceNotFoundException;
}
