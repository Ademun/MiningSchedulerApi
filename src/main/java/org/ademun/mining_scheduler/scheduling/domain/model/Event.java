package org.ademun.mining_scheduler.scheduling.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.Duration;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

  @EmbeddedId
  protected EventId id;
  @Column
  protected String title;
  @Column
  protected String description;
  @ManyToOne
  @JoinColumn(name = "day_id")
  protected Day day;
  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "start", column = @Column(name = "start_time")),
      @AttributeOverride(name = "end", column = @Column(name = "end_time"))})
  protected TimePeriod timePeriod;

  public Duration getDuration() {
    return timePeriod.duration();
  }

  public boolean isOccurring(LocalTime time) {
    return time.isAfter(timePeriod.start()) && time.isBefore(timePeriod.end());
  }
}
