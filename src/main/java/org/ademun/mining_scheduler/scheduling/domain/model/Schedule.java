package org.ademun.mining_scheduler.scheduling.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.ademun.mining_scheduler.scheduling.application.usecase.exception.ResourceNotFoundException;
import org.ademun.mining_scheduler.scheduling.domain.exception.ModelInvariantException;

@Getter
@Setter
@Builder
@Entity
@Table(name = "schedule")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Schedule {

  @EmbeddedId
  private ScheduleId id;
  @Column
  private String name;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
  @Exclude
  private List<Week> weeks = new ArrayList<>();
  private Integer activeWeekIndex;

  public void addWeek() {
    if (weeks.size() >= 5) {
      throw new ModelInvariantException("Cannot add more than 5 weeks");
    }
    Week week = new Week();
    week.setSchedule(this);
    weeks.add(week);
    if (weeks.size() == 1) {
      activeWeekIndex = 0;
    }
  }

  public void removeWeek(Integer weekIndex) {
    if (weekIndex < 0 || weekIndex >= weeks.size()) {
      throw new IllegalArgumentException(
          String.format("Week index %d out of range %d", weekIndex, weeks.size()));
    }
    Week weekToRemove = weeks.get(weekIndex);
    weeks.remove(weekToRemove);
    weekToRemove.setSchedule(null);
  }

  public void addEvent(Integer weekIndex, DayOfWeek day, Event event) {
    Day targetDay = getDayByWeekByDayOfWeek(weekIndex, day);
    targetDay.addEvent(event);
  }

  public void removeEvent(EventId eventId) {
    Event event = findEventById(eventId).orElseThrow(() -> new ResourceNotFoundException(
        String.format("Event with value %s not found", eventId)));
    Day day = event.getDay();
    day.removeEvent(event);
  }

  public void rotateWeek() {
    if (weeks.isEmpty()) {
      return;
    }
    activeWeekIndex = (activeWeekIndex + 1) % weeks.size();
  }

  public Day getDayByWeekByDayOfWeek(Integer weekIndex, DayOfWeek dayOfWeek) {
    if (weekIndex == null) {
      weekIndex = activeWeekIndex;
    }
    return weeks.get(weekIndex).getDay(dayOfWeek);
  }

  private Optional<Event> findEventById(EventId id) {
    return weeks.stream()
        .flatMap(week -> week.getAllDays().stream())
        .flatMap(day -> day.getAllEvents().stream())
        .filter(event -> event.getId().equals(id))
        .findFirst();
  }
}
