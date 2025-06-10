package org.ademun.mining_scheduler.scheduling.domain;

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

  public void addWeek(Week week) {
    if (weeks.size() >= 5) {
      throw new RuntimeException();
    }
    week.setSchedule(this);
    weeks.add(week);
  }

  public void removeWeek(Integer weekNumber) {
    Week weekToRemove = weeks.get(weekNumber);
    weeks.remove(weekToRemove);
    weekToRemove.setSchedule(null);
  }

  public void addEvent(Integer weekNumber, DayOfWeek day, Event event) {
    Day targetDay = getDayByWeekByDayOfWeek(weekNumber, day);
    targetDay.addEvent(event);
  }

  public void removeEvent(EventId eventId) {
    Event event = findEventById(eventId).orElseThrow();
    Day day = event.getDay();
    day.removeEvent(event);
  }

  public Day getToday(Integer weekNumber) {
    return getDayByWeekByDayOfWeek(weekNumber, LocalDate.now().getDayOfWeek());
  }

  public Day getTomorrow(Integer weekNumber) {
    return getDayByWeekByDayOfWeek(weekNumber, LocalDate.now().getDayOfWeek().plus(1));
  }

  public Event getCurrentEvent(Integer weekNumber) {
    return getToday(weekNumber).findCurrentEvent().orElseThrow();
  }

  public Event getNextEvent(Integer weekNumber) {
    return getToday(weekNumber).findNextEvent().orElseThrow();
  }

  private Day getDayByWeekByDayOfWeek(Integer weekNumber, DayOfWeek dayOfWeek) {
    return weeks.get(weekNumber - 1).getDays().get(dayOfWeek.ordinal());
  }

  private Optional<Event> findEventById(EventId id) {
    return weeks.stream()
        .flatMap(week -> week.getDays().stream())
        .flatMap(day -> day.getEvents().stream())
        .filter(event -> event.getId().equals(id))
        .findFirst();
  }
}
