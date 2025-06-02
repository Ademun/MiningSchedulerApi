package org.ademun.mining_scheduler.service.impl;

import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.repository.ScheduleRepository;
import org.ademun.mining_scheduler.service.ScheduleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

  private final ScheduleRepository scheduleRepository;

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
  public Schedule findByGroupIdAndWeek(UUID groupId, Short week) throws ResourceNotFoundException {
    return scheduleRepository.findByGroup_IdAndWeek(groupId, week)
        .orElseThrow(() -> new ResourceNotFoundException("No such schedule"));
  }

  private Day getDay(UUID id, DayOfWeek dayOfWeek) throws ResourceNotFoundException {
    return findById(id).getDays().stream().filter(day -> day.getDayOfWeek().equals(dayOfWeek))
        .findFirst().orElseThrow(() -> new ResourceNotFoundException("No such day"));
  }

  @Override
  public Day getCurrentDay(UUID id) throws ResourceNotFoundException {
    DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
    return getDay(id, currentDay);
  }

  @Override
  public Day getNextDay(UUID id) throws ResourceNotFoundException {
    DayOfWeek nextDay = LocalDate.now().getDayOfWeek().plus(1);
    return getDay(id, nextDay);
  }

  @Override
  public List<Day> getDays(UUID id) throws ResourceNotFoundException {
    return findById(id).getDays().stream().toList();
  }

  @Override
  @Transactional
  public void addDay(UUID id, Day day)
      throws ResourceNotFoundException {
    Schedule schedule = findById(id);
    //TODO: Add exception message
    if (schedule.getDays().size() >= schedule.getNumberOfDays()) {
      throw new RuntimeException();
    }
    schedule.getDays().add(day);
    day.setSchedule(schedule);
    scheduleRepository.save(schedule);
  }

  @Override
  @Transactional
  public void removeDay(UUID id, DayOfWeek dayOfWeek) throws ResourceNotFoundException {
    Schedule schedule = findById(id);
    Day remove = getDay(id, dayOfWeek);
    schedule.getDays().remove(remove);
    scheduleRepository.save(schedule);
  }
}
