package org.ademun.mining_scheduler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString.Exclude;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "student_group", indexes = {
    @Index(name = "idx_group_name_unq", columnList = "name", unique = true),
    @Index(name = "idx_group_chat_id_unq", columnList = "chat_id", unique = true)
})
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "chat_id", nullable = false)
  private Long chatId;

  @OneToMany(mappedBy = "group")
  @Exclude
  private Set<Student> students = new LinkedHashSet<>();

  @OneToMany(mappedBy = "group", orphanRemoval = true)
  @Exclude
  private Set<Schedule> schedules = new LinkedHashSet<>();

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer()
            .getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
            .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Group group = (Group) o;
    return getId() != null && Objects.equals(getId(), group.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}
