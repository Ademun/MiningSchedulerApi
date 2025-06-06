package org.ademun.mining_scheduler.repository;

import java.util.UUID;
import org.ademun.mining_scheduler.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

}
