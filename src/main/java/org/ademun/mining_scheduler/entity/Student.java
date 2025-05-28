package org.ademun.mining_scheduler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "surname", nullable = false)
  private String surname;
  @Column(name = "patronymic", nullable = false)
  private String patronymic;

  @ManyToOne(optional = false)
  @JoinColumn(name = "group_id", nullable = false, unique = true)
  private Group group;

  public String getFullName() {
    return name + " " + surname + " " + patronymic;
  }
}
