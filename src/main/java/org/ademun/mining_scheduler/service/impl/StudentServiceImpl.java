package org.ademun.mining_scheduler.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Student;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.repository.StudentRepository;
import org.ademun.mining_scheduler.service.StudentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;

  @Override
  public Student create(Student student) {
    return studentRepository.save(student);
  }

  @Override
  public Student findById(UUID id) throws ResourceNotFoundException {
    return studentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No such student"));
  }

  @Override
  public Student findByFullName(String fullName) throws ResourceNotFoundException {
    String[] split = fullName.split(" ");
    return studentRepository.findByFullName(split[0], split[1], split[2])
        .orElseThrow(() -> new ResourceNotFoundException("No such student"));
  }

  @Override
  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  @Override
  public void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException {
    Student student = findById(id);
    studentRepository.delete(student);
  }

  @Override
  public Group getGroup(UUID id) throws ResourceNotFoundException {
    return findById(id).getGroup();
  }
}
