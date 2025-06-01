package org.ademun.mining_scheduler.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Lesson;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.repository.DayRepository;
import org.ademun.mining_scheduler.service.DayService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DayServiceImpl implements DayService {

  private final DayRepository dayRepository;

  //TODO: Implement check for duplicate days on same week
  @Override
  public Day create(Day day) throws ResourceAlreadyExistsException {
    return dayRepository.save(day);
  }

  @Override
  public Day findById(UUID id) throws ResourceNotFoundException {
    return dayRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No such day"));
  }

  @Override
  public List<Day> findAll() {
    return dayRepository.findAll();
  }

  @Override
  public void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException {
    Day day = findById(id);
    dayRepository.delete(day);
  }

  @Override
  public List<Lesson> getLessons(UUID id) throws ResourceNotFoundException {
    return findById(id).getLessons().stream().toList();
  }

  @Override
  public void addLesson(UUID id, Lesson lesson)
      throws ResourceNotFoundException, ResourceAlreadyExistsException {
    Day day = findById(id);
    day.getLessons().add(lesson);
    lesson.setDay(day);
    dayRepository.save(day);
  }

  @Override
  public void removeLesson(UUID id, UUID lessonId) throws ResourceNotFoundException {
    Day day = findById(id);
    Lesson remove = day.getLessons().stream().filter(lesson -> lesson.getId().equals(lessonId))
        .findFirst().orElseThrow(() -> new ResourceNotFoundException("No such lesson"));
    day.getLessons().remove(remove);
    dayRepository.save(day);
  }
}
