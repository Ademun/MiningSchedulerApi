package org.ademun.mining_scheduler.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.entity.Lesson;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.repository.LessonRepository;
import org.ademun.mining_scheduler.service.LessonService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

  private final LessonRepository lessonRepository;

  @Override
  public Lesson create(Lesson lesson) {
    return lessonRepository.save(lesson);
  }

  @Override
  public Lesson findById(UUID id) throws ResourceNotFoundException {
    return lessonRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No such lesson"));
  }

  @Override
  public List<Lesson> findAll() {
    return lessonRepository.findAll();
  }

  @Override
  public void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException {
    Lesson lesson = findById(id);
    lessonRepository.delete(lesson);
  }
}
