package org.ademun.mining_scheduler.repository;

import java.util.UUID;
import org.ademun.mining_scheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
}
