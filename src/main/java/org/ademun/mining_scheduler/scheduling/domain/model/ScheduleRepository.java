package org.ademun.mining_scheduler.scheduling.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleId> {

}
