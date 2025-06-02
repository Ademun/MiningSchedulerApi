package org.ademun.mining_scheduler.facade.impl;

import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Lesson;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;
import org.ademun.mining_scheduler.facade.GroupFacade;
import org.ademun.mining_scheduler.service.DayService;
import org.ademun.mining_scheduler.service.GroupService;
import org.ademun.mining_scheduler.service.ScheduleService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupFacadeImpl implements GroupFacade {

  private final GroupService groupService;
  private final ScheduleService scheduleService;
  private final DayService dayService;

  @Override
  @Transactional
  public Group createGroup(Group group) throws ResourceAlreadyExistsException {
    if (groupService.existsByName(group.getName()) || groupService.existsByChatId(
        group.getChatId())) {
      throw new ResourceAlreadyExistsException("Group already exists");
    }
    Schedule basicSchedule = generateSchedule((short) 5);
    Group createdGroup = groupService.save(group);
    createdGroup.getSchedules().add(basicSchedule);
    basicSchedule.setGroup(createdGroup);
    return groupService.save(createdGroup);
  }

  @Override
  public Schedule generateSchedule(Short numberOfDays) {
    Schedule schedule = new Schedule();
    schedule.setWeek((short) 0);
    schedule.setNumberOfDays(numberOfDays);

    for (DayOfWeek dayOfWeek : Arrays.copyOfRange(DayOfWeek.values(), 0, numberOfDays)) {
      Day day = new Day();
      day.setDayOfWeek(dayOfWeek);
      day.setSchedule(schedule);
      schedule.getDays().add(day);
    }
    return schedule;
  }

  @Override
  public Schedule findGroupScheduleByWeek(UUID groupId, Short week) {
    return scheduleService.findByGroupIdAndWeek(groupId, week);
  }

  @Override
  public Day findDayByScheduleByDayOfWeek(UUID scheduleId, DayOfWeek dayOfWeek) {
    return dayService.findByScheduleIdAndDayOfWeek(scheduleId, dayOfWeek);
  }

  @Override
  public Day getCurrentDay(UUID scheduleId) {
    return findDayByScheduleByDayOfWeek(scheduleId, LocalDate.now().getDayOfWeek());
  }

  @Override
  public Day getNextDay(UUID scheduleId) {
    return findDayByScheduleByDayOfWeek(scheduleId, LocalDate.now().getDayOfWeek().plus(1));
  }

  @Override
  public Day generateDay(UUID scheduleId, DayOfWeek dayOfWeek, List<Lesson> lessons) {
    return null;
  }
}
