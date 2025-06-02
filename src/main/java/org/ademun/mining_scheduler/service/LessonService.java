package org.ademun.mining_scheduler.service;

import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Lesson;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;

public interface LessonService {

  Lesson create(Lesson lesson);

  Lesson findById(UUID id) throws ResourceNotFoundException;

  List<Lesson> findAll();

  void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException;
}
