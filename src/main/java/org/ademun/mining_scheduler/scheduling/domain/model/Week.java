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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "week")
@AllArgsConstructor
public class Week {

  @EmbeddedId
  private WeekId id;
  @ManyToOne
  @JoinColumn(name = "schedule_id")
  private Schedule schedule;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "week", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Day> days;

  public Week() {
    this.days = Arrays.stream(DayOfWeek.values())
        .map(dayOfWeek -> {
          Day day = new Day();
          day.setId((new DayId(UUID.randomUUID())));
          day.setDayOfWeek(dayOfWeek);
          day.setWeek(this);
          return day;
        })
        .toList();
  }

  @PostPersist
  @PostUpdate
  public void validateDays() {
    if (days.size() != 7) {
      throw new RuntimeException();
    }
  }
}
