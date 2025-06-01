package org.ademun.mining_scheduler.repository;

import java.util.Optional;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

  @Query("select t from Teacher t where" + " lower(t.name) = lower(:name)"
      + "and lower(t.surname) = lower(:surname)" + "and lower(t.patronymic) = lower(:patronymic)")
  Optional<Teacher> findByFullName(String name, String surname, String patronymic);

}
