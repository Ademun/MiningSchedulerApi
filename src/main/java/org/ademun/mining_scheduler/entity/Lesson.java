package org.ademun.mining_scheduler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.ademun.mining_scheduler.entity.type.LessonType;

@Getter
@Setter
@Entity
@Table(name = "lesson")
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;
  @Column(name = "start_time", nullable = false)
  private LocalTime startTime;
  @Column(name = "end_time", nullable = false)
  private LocalTime endTime;
  @Column(name = "type", nullable = false)
  private LessonType type;
  @Column(name = "building", nullable = false)
  private Short building;
  @Column(name = "classroom", nullable = false)
  private Short classroom;

  @ManyToOne(optional = false)
  @JoinColumn(name = "day_id", nullable = false)
  private Day day;

  @ManyToOne(optional = false)
  @JoinColumn(name = "subject_id", nullable = false)
  private Subject subject;

  @ManyToOne(optional = false)
  @JoinColumn(name = "teacher_id", nullable = false)
  private Teacher teacher;

}
