package org.ademun.mining_scheduler.facade;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Day;
import org.ademun.mining_scheduler.entity.Group;
import org.ademun.mining_scheduler.entity.Lesson;
import org.ademun.mining_scheduler.entity.Schedule;
import org.ademun.mining_scheduler.exception.ResourceAlreadyExistsException;

public interface GroupFacade {

  Group createGroup(Group group) throws ResourceAlreadyExistsException;

  Schedule generateSchedule(Short numberOfDays);

  Schedule findGroupScheduleByWeek(UUID groupId, Short week);

  Day findDayByScheduleByDayOfWeek(UUID scheduleId, DayOfWeek dayOfWeek);

  Day getCurrentDay(UUID scheduleId);

  Day getNextDay(UUID scheduleId);

  Day generateDay(UUID scheduleId, DayOfWeek dayOfWeek, List<Lesson> lessons);
}
