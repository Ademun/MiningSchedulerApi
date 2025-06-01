package org.ademun.mining_scheduler.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;

public interface ScheduleService {

  Schedule create(Schedule schedule);

  Schedule findById(UUID id) throws ResourceNotFoundException;

  List<Schedule> findAll();

  void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException;

  List<Day> getDays(UUID id) throws ResourceNotFoundException;

  Day getDayByDayOfWeek(UUID id, DayOfWeek dayOfWeek) throws ResourceNotFoundException;

  Day getCurrentDay(UUID id) throws ResourceNotFoundException;

  Day getNextDay(UUID id) throws ResourceNotFoundException;

  void addDay(UUID id, Day day)
      throws ResourceNotFoundException, ResourceAlreadyExistsException;

  void removeDay(UUID id, DayOfWeek dayOfWeek)
      throws ResourceNotFoundException;

}
