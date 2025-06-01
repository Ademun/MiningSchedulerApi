package org.ademun.mining_scheduler.service.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.entity.Subject;
import org.ademun.mining_scheduler.entity.Teacher;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.repository.SubjectRepository;
import org.ademun.mining_scheduler.service.SubjectService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

  private final SubjectRepository subjectRepository;

  @Override
  public Subject create(Subject subject) throws ResourceAlreadyExistsException {
    if (subjectRepository.isPresent(subject.getName())) {
      throw new ResourceAlreadyExistsException("Subject already exists");
    }
    return subjectRepository.save(subject);
  }

  @Override
  public Subject findById(UUID id) throws ResourceNotFoundException {
    return subjectRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No such subject"));
  }

  @Override
  public Subject findByName(String subjectName) throws ResourceNotFoundException {
    return subjectRepository.findByName(subjectName)
        .orElseThrow(() -> new ResourceNotFoundException("No such subject"));
  }

  @Override
  public List<Subject> findAll() {
    return subjectRepository.findAll();
  }

  @Override
  public void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException {
    Subject subject = findById(id);
    if (!subject.getTeachers().isEmpty()) {
      throw new ResourceIsBeingUsedException("Cant delete a subject with teachers");
    }
    subjectRepository.delete(subject);
  }

  @Override
  public List<Teacher> getTeachers(UUID id) throws ResourceNotFoundException {
    return findById(id).getTeachers().stream()
        .toList();
  }

  @Override
  @Transactional
  public void addTeacher(UUID id, Teacher teacher) throws ResourceNotFoundException {
    Subject subject = findById(id);
    subject.getTeachers().add(teacher);
    teacher.setSubject(subject);
    subjectRepository.save(subject);
  }

  @Override
  @Transactional
  public void removeTeacher(UUID id, UUID teacherId) throws ResourceNotFoundException {
    Subject subject = findById(id);
    Teacher remove = subject.getTeachers().stream()
        .filter(teacher -> teacher.getId().equals(teacherId))
        .findFirst().orElseThrow(() -> new ResourceNotFoundException("No such teacher"));
    subject.getTeachers().remove(remove);
    remove.setSubject(null);
    subjectRepository.save(subject);
  }
}
