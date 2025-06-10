package org.ademun.mining_scheduler.scheduling.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "day")
@NoArgsConstructor
@AllArgsConstructor
public class Day {

  @EmbeddedId
  private DayId id;
  @Column
  private DayOfWeek dayOfWeek;
  @ManyToOne
  @JoinColumn(name = "week_id")
  private Week week;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Event> events = new ArrayList<>();
  @Transient
  private Duration totalDuration;

  @PostLoad
  private void calculateTotalDuration() {
    totalDuration = events.stream().map(Event::getDuration).reduce(Duration.ZERO, Duration::plus);
  }

  public void addEvent(Event event) {
    Duration newTotalDuration = totalDuration.plus(event.getDuration());
    if (newTotalDuration.compareTo(Duration.ofHours(24)) > 0) {
      throw new RuntimeException();
    }
    event.setDay(this);
    events.add(event);
    totalDuration = newTotalDuration;
  }

  public void removeEvent(Event event) {
    events.remove(event);
    event.setDay(null);
    totalDuration = totalDuration.minus(event.getDuration());
  }

  public Optional<Event> findCurrentEvent() {
    return events.stream().filter(event -> event.isOccurring(LocalTime.now())).findFirst();
  }

  public Optional<Event> findNextEvent() {
    return events.stream().filter(event -> event.getTimePeriod().start().isAfter(LocalTime.now()))
        .findFirst();
  }
}
