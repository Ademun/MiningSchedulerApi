package org.ademun.mining_scheduler.scheduling.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
@Table(name = "schedule")
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

  @EmbeddedId
  private ScheduleId id;
  @Column
  private String name;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Week> weeks = new ArrayList<>();
  private Integer activeWeekIndex;

  public void addWeek(Week week) {
    if (weeks.size() >= 5) {
      throw new RuntimeException();
    }
    week.setSchedule(this);
    weeks.add(week);
    if (weeks.size() == 1) {
      activeWeekIndex = 0;
    }
  }

  public void removeWeek(Integer weekIndex) {
    Week weekToRemove = weeks.get(weekIndex);
    weeks.remove(weekToRemove);
    weekToRemove.setSchedule(null);
  }

  public void addEvent(Integer weekIndex, DayOfWeek day, Event event) {
    Day targetDay = getDayByWeekByDayOfWeek(weekIndex, day);
    targetDay.addEvent(event);
  }

  public void removeEvent(EventId eventId) {
    Event event = findEventById(eventId).orElseThrow();
    Day day = event.getDay();
    day.removeEvent(event);
  }

  public Day getToday() {
    return getDayByWeekByDayOfWeek(activeWeekIndex, LocalDate.now().getDayOfWeek());
  }

  public Day getTomorrow() {
    if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY) {
      return getDayByWeekByDayOfWeek(activeWeekIndex + 1, LocalDate.now().getDayOfWeek().plus(1));
    }
    return getDayByWeekByDayOfWeek(activeWeekIndex, LocalDate.now().getDayOfWeek().plus(1));
  }

  public Event getCurrentEvent() {
    return getToday().findCurrentEvent().orElseThrow();
  }

  public Event getNextEvent() {
    return getToday().findNextEvent().orElseThrow();
  }

  public void rotateWeek() {
    if (weeks.isEmpty()) {
      return;
    }
    activeWeekIndex = (activeWeekIndex + 1) % weeks.size();
  }

  public Day getDayByWeekByDayOfWeek(Integer weekIndex, DayOfWeek dayOfWeek) {
    return weeks.get(weekIndex).getDays().get(dayOfWeek.ordinal());
  }

  private Optional<Event> findEventById(EventId id) {
    return weeks.stream()
        .flatMap(week -> week.getDays().stream())
        .flatMap(day -> day.getAllEvents().stream())
        .filter(event -> event.getId().equals(id))
        .findFirst();
  }
}
