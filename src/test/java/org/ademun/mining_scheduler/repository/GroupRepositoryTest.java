package org.ademun.mining_scheduler.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.ademun.mining_scheduler.entity.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class GroupRepositoryTest {

  @Autowired
  private GroupRepository groupRepository;

  @Test
  public void findByName_ExistingName_ReturnsGroup() {
    Group group = new Group();
    group.setName("Test");
    group.setChatId(1L);
    groupRepository.save(group);

    Optional<Group> found = groupRepository.findByName(group.getName());

    assertTrue(found.isPresent());
    assertEquals(group.getName(), found.get().getName());
  }

  @Test
  public void findByName_NonExistingName_ReturnsEmpty() {
    Group group = new Group();
    group.setName("Test");
    group.setChatId(1L);
    groupRepository.save(group);

    Optional<Group> found = groupRepository.findByName("Invalid");

    assertTrue(found.isEmpty());
  }

  @Test
  public void findByName_MixedName_ReturnsGroup() {
    Group group = new Group();
    group.setName("Test");
    group.setChatId(1L);
    groupRepository.save(group);

    Optional<Group> found = groupRepository.findByName("T e ST");

    assertTrue(found.isPresent());
    assertEquals(group.getName(), found.get().getName());
  }

  @Test
  public void findByChatId_ExistingChatId_ReturnsGroup() {
    Group group = new Group();
    group.setName("Test");
    group.setChatId(1L);
    groupRepository.save(group);

    Optional<Group> found = groupRepository.findByChatId(group.getChatId());

    assertTrue(found.isPresent());
    assertEquals(group.getName(), found.get().getName());
  }

  @Test
  public void findByChatId_NonExistingChatId_ReturnsEmpty() {
    Group group = new Group();
    group.setName("Test");
    group.setChatId(1L);
    groupRepository.save(group);

    Optional<Group> found = groupRepository.findByChatId(2);

    assertTrue(found.isEmpty());
  }

  @Test
  public void isPresent_ExistingName_ReturnsTrue() {
    Group group = new Group();
    group.setName("Test");
    group.setChatId(1L);
    groupRepository.save(group);

    boolean found = groupRepository.isPresent(group.getName());

    assertTrue(found);
  }

  @Test
  public void isPresent_NonExistingName_ReturnsFalse() {
    Group group = new Group();
    group.setName("Test");
    group.setChatId(1L);
    groupRepository.save(group);

    boolean found = groupRepository.isPresent("Invalid");

    assertFalse(found);
  }
}
