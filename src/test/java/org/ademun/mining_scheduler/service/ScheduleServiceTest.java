package org.ademun.mining_scheduler.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.repository.ScheduleRepository;
import org.ademun.mining_scheduler.service.impl.ScheduleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

  @InjectMocks
  private ScheduleServiceImpl scheduleService;
  @Mock
  private ScheduleRepository scheduleRepository;

  @Test
  public void create_NewSchedule_ReturnsSchedule() {
    Schedule schedule = new Schedule();
    schedule.setId(UUID.randomUUID());
    schedule.setWeek((short) 1);

    when(scheduleRepository.save(schedule)).thenReturn(schedule);

    Schedule created = scheduleService.create(schedule);
    assertNotNull(created.getId());
    assertEquals(schedule.getId(), created.getId());
  }

  @Test
  public void getCurrentDay_ReturnsCurrentDay() {
    Schedule schedule = new Schedule();
    schedule.setId(UUID.randomUUID());
    schedule.setWeek((short) 1);
    Day day = new Day();
    day.setId(UUID.randomUUID());
    day.setDayOfWeek(LocalDate.now().getDayOfWeek());
    schedule.getDays().add(day);

    when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));

    assertEquals(day, scheduleService.getCurrentDay(schedule.getId()));
  }

  @Test
  public void getNextDay_ReturnsNextDay() {
    Schedule schedule = new Schedule();
    schedule.setId(UUID.randomUUID());
    schedule.setWeek((short) 1);
    Day day = new Day();
    day.setId(UUID.randomUUID());
    day.setDayOfWeek(LocalDate.now().getDayOfWeek().plus(1));
    schedule.getDays().add(day);

    when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));

    assertEquals(day, scheduleService.getNextDay(schedule.getId()));
  }
}
