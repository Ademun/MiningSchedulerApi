package org.ademun.mining_scheduler.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.exception.ResourceIsBeingUsedException;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.repository.ScheduleRepository;
import org.ademun.mining_scheduler.service.ScheduleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

  private final ScheduleRepository scheduleRepository;

  //TODO: Implement check for duplicate schedules on same week number
  @Override
  public Schedule create(Schedule schedule) throws ResourceAlreadyExistsException {
    return scheduleRepository.save(schedule);
  }

  @Override
  public Schedule findById(UUID id) throws ResourceNotFoundException {
    return scheduleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No such schedule"));
  }

  @Override
  public List<Schedule> findAll() {
    return scheduleRepository.findAll();
  }

  @Override
  public void delete(UUID id) throws ResourceNotFoundException, ResourceIsBeingUsedException {
    Schedule schedule = findById(id);
    scheduleRepository.delete(schedule);
  }

  @Override
  public List<Day> getDays(UUID id) throws ResourceNotFoundException {
    return findById(id).getDays().stream().toList();
  }

  @Override
  public Day getDayByDayOfWeek(UUID id, DayOfWeek dayOfWeek) throws ResourceNotFoundException {
    return findById(id).getDays().stream().filter(day -> day.getDayOfWeek().equals(dayOfWeek))
        .findFirst().orElseThrow(() -> new ResourceNotFoundException("No such day"));
  }

  @Override
  public Day getCurrentDay(UUID id) throws ResourceNotFoundException {
    DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
    return getDayByDayOfWeek(id, currentDay);
  }

  @Override
  public Day getNextDay(UUID id) throws ResourceNotFoundException {
    DayOfWeek nextDay = LocalDate.now().getDayOfWeek().plus(1);
    return getDayByDayOfWeek(id, nextDay);
  }

  @Override
  public void addDay(UUID id, Day day)
      throws ResourceNotFoundException, ResourceAlreadyExistsException {
    Schedule schedule = findById(id);
    schedule.getDays().add(day);
    day.setSchedule(schedule);
    scheduleRepository.save(schedule);
  }

  @Override
  public void removeDay(UUID id, DayOfWeek dayOfWeek) throws ResourceNotFoundException {
    Schedule schedule = findById(id);
    Day remove = getDayByDayOfWeek(id, dayOfWeek);
    schedule.getDays().remove(remove);
    scheduleRepository.save(schedule);
  }
}
