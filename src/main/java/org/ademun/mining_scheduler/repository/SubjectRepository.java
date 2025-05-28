package org.ademun.mining_scheduler.repository;

import java.util.Optional;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {

  @Query("select s from Subject s where replace(lower(s.name), ' ', '') = replace(lower(:name), ' ', '')")
  Optional<Subject> findByName(String name);

  @Query("select case when (count(s) > 0) then true else false end from Subject s where replace(lower(s.name), ' ', '') = replace(lower(:name), ' ', '')")
  boolean isPresent(String name);
}
