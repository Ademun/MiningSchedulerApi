package org.ademun.mining_scheduler.scheduling.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Getter
@Setter
@Entity
@Table(name = "week")
@Builder
@AllArgsConstructor
@ToString
public class Week {

  @EmbeddedId
  private WeekId id;
  @ManyToOne
  @JoinColumn(name = "schedule_id")
  private Schedule schedule;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "week", cascade = CascadeType.ALL, orphanRemoval = true)
  @Exclude
  private List<Day> days = new ArrayList<>();

  public Week() {
    this.id = new WeekId(UUID.randomUUID());
    Arrays.stream(DayOfWeek.values()).forEach(dayOfWeek -> {
      Day day = Day.builder()
          .id(new DayId(UUID.randomUUID()))
          .dayOfWeek(dayOfWeek)
          .week(this)
          .build();
      days.add(day);
    });
  }

  @PostPersist
  @PostUpdate
  public void validateDays() {
    if (days.size() != 7) {
      throw new RuntimeException();
    }
  }
}
