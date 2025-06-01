package org.ademun.mining_scheduler.repository;

import java.util.Optional;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

  @Query("select t from Student t where" + " lower(t.name) = lower(:name)"
      + "and lower(t.surname) = lower(:surname)" + "and lower(t.patronymic) = lower(:patronymic)")
  Optional<Student> findByFullName(String name, String surname, String patronymic);

}
