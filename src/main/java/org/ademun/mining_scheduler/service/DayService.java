package org.ademun.mining_scheduler.service;

import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Lesson;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;

public interface DayService {

  Day create(Day day);

  Day findById(UUID id) throws ResourceNotFoundException;

  List<Day> findAll();

  void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException;

  List<Lesson> getLessons(UUID id) throws ResourceNotFoundException;

  void addLesson(UUID id, Lesson lesson)
      throws ResourceNotFoundException, ResourceAlreadyExistsException;

  void removeLesson(UUID id, UUID lessonId) throws ResourceNotFoundException;
}
