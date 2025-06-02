package org.ademun.mining_scheduler.repository;

import java.util.Optional;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

  Optional<Schedule> findByGroup_IdAndWeek(UUID groupId, Short week);
}
