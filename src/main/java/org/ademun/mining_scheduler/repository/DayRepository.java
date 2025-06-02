package org.ademun.mining_scheduler.repository;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<Day, UUID> {

  Optional<Day> findBySchedule_IdAndDayOfWeek(UUID scheduleId, DayOfWeek dayOfWeek);
}
