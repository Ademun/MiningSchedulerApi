package org.ademun.mining_scheduler.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;

public interface ScheduleService {

  Schedule findById(UUID id) throws ResourceNotFoundException;

  List<Schedule> findAll();

  Schedule findByGroupIdAndWeek(UUID groupId, Short week) throws ResourceNotFoundException;

  Day getCurrentDay(UUID id) throws ResourceNotFoundException;

  Day getNextDay(UUID id) throws ResourceNotFoundException;

  List<Day> getDays(UUID id) throws ResourceNotFoundException;

  void addDay(UUID id, Day day) throws ResourceNotFoundException;

  void removeDay(UUID id, DayOfWeek dayOfWeek) throws ResourceNotFoundException;
}
