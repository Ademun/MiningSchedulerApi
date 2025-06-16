package org.ademun.mining_scheduler.scheduling.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import org.ademun.mining_scheduler.scheduling.domain.exception.EventDurationOverflowException;
import org.ademun.mining_scheduler.scheduling.domain.exception.UnknownEventTypeException;

@Getter
@Setter
@Entity
@Table(name = "day")
public class Day {

  @EmbeddedId
  private DayId id;
  @Column
  private DayOfWeek dayOfWeek;
  @ManyToOne
  @JoinColumn(name = "week_id")
  private Week week;
  @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RecurringEvent> recurringEvents = new ArrayList<>();

  @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TemporaryEvent> temporaryEvents = new ArrayList<>();
  @Transient
  private Duration totalDuration;

  @PostLoad
  private void calculateTotalDuration() {
    totalDuration = recurringEvents.stream()
        .map(Event::getDuration)
        .reduce(Duration.ZERO, Duration::plus);
  }

  public void addEvent(Event event) {
    switch (event) {
      case RecurringEvent r -> addRecurringEvent(r);
      case TemporaryEvent t -> addTemporaryEvent(t);
      default -> throw new UnknownEventTypeException(String.format("Unknown event: %s", event));
    }
  }

  private void addRecurringEvent(RecurringEvent event) {
    Duration newTotalDuration = totalDuration.plus(event.getDuration());
    if (newTotalDuration.compareTo(Duration.ofHours(24)) > 0) {
      throw new EventDurationOverflowException("Total duration of events exceeds 24 hours");
    }
    totalDuration = newTotalDuration;
    if (recurringEvents.stream()
        .anyMatch(e -> e.getTimePeriod().overlapsWith(event.getTimePeriod()))) {
      throw new RuntimeException();
    }
    recurringEvents.add(event);
  }

  private void addTemporaryEvent(TemporaryEvent event) {
    if (temporaryEvents.stream()
        .anyMatch(e -> e.getTimePeriod().overlapsWith(event.getTimePeriod()))) {
      throw new RuntimeException();
    }
    temporaryEvents.add(event);
  }

  public List<Event> getAllEvents() {
    return Stream.of(recurringEvents, temporaryEvents)
        .flatMap(List::stream)
        .map(event -> (Event) event)
        .toList();
  }

  public void removeEvent(Event event) {
    switch (event) {
      case RecurringEvent r -> recurringEvents.remove(r);
      case TemporaryEvent t -> temporaryEvents.remove(t);
      default -> throw new RuntimeException();
    }
    event.setDay(null);
    totalDuration = totalDuration.minus(event.getDuration());
  }

  public Optional<Event> findCurrentEvent() {
    return getAllEvents().stream().filter(event -> event.isOccurring(LocalTime.now())).findFirst();
  }

  public Optional<Event> findNextEvent() {
    return getAllEvents().stream()
        .filter(event -> event.getTimePeriod().start().isAfter(LocalTime.now()))
        .findFirst();
  }

}
