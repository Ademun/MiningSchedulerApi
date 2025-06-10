package org.ademun.mining_scheduler.scheduling.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
public class Event {

  @EmbeddedId
  private EventId id;
  @Column
  private String title;
  @Column
  private String description;
  @ManyToOne
  @JoinColumn(name = "day_id")
  private Day day;
  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "start", column = @Column(name = "start_time")),
      @AttributeOverride(name = "end", column = @Column(name = "end_time"))})
  private TimePeriod timePeriod;

  public Duration getDuration() {
    return timePeriod.duration();
  }

  public boolean isOccurring(LocalTime time) {
    return time.isAfter(timePeriod.start()) && time.isBefore(timePeriod.end());
  }
}
