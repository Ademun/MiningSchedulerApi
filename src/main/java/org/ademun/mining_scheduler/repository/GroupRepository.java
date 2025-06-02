package org.ademun.mining_scheduler.repository;

import java.util.Optional;
import java.util.UUID;
import org.ademun.mining_scheduler.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

  @Query("select g from Group g where replace(lower(g.name), ' ', '') = replace(lower(:name), ' ', '')")
  Optional<Group> findByName(String name);

  Optional<Group> findByChatId(long chatId);

  boolean existsByChatId(long chatId);

  boolean existsByName(String name);
}
