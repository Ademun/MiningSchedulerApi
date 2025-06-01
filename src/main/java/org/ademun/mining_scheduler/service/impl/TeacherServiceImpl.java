package org.ademun.mining_scheduler.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.entity.Subject;
import org.ademun.mining_scheduler.entity.Teacher;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.repository.TeacherRepository;
import org.ademun.mining_scheduler.service.TeacherService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

  private final TeacherRepository teacherRepository;

  @Override
  public Teacher create(Teacher teacher) {
    return teacherRepository.save(teacher);
  }

  @Override
  public Teacher findById(UUID id) throws ResourceNotFoundException {
    return teacherRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No such teacher"));
  }

  @Override
  public Teacher findByFullName(String fullName) throws ResourceNotFoundException {
    String[] split = fullName.split(" ");
    return teacherRepository.findByFullName(split[0], split[1], split[2])
        .orElseThrow(() -> new ResourceNotFoundException("No such teacher"));
  }

  @Override
  public List<Teacher> findAll() {
    return teacherRepository.findAll();
  }

  @Override
  public void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException {
    Teacher teacher = findById(id);
    teacherRepository.delete(teacher);
  }

  @Override
  public Subject getSubject(UUID id) throws ResourceNotFoundException {
    return findById(id).getSubject();
  }
}
