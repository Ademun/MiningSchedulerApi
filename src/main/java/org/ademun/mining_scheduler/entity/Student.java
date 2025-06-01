package org.ademun.mining_scheduler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@RequiredArgsConstructor
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
  @JoinColumn(name = "group_id")
  private Group group;

  public String getFullName() {
    return name + " " + surname + " " + patronymic;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass = o instanceof HibernateProxy
        ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
        : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Student student = (Student) o;
    return getId() != null && Objects.equals(getId(), student.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}
