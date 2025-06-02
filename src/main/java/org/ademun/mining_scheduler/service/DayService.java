package org.ademun.mining_scheduler.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Lesson;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;

public interface DayService {

  Day findById(UUID id) throws ResourceNotFoundException;

  Day findByScheduleIdAndDayOfWeek(UUID scheduleId, DayOfWeek dayOfWeek)
      throws ResourceNotFoundException;

  List<Lesson> getLessons(UUID id) throws ResourceNotFoundException;

  void addLesson(UUID id, Lesson lesson)
      throws ResourceNotFoundException, ResourceAlreadyExistsException;

  void removeLesson(UUID id, UUID lessonId) throws ResourceNotFoundException;
}
