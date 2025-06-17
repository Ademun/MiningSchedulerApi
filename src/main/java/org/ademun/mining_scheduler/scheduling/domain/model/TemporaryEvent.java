package org.ademun.mining_scheduler.scheduling.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "temporary_event")
public class TemporaryEvent extends Event {

  @Column
  private LocalDate date;

  public LocalDateTime getStartDateTime() {
    return LocalDateTime.of(date, timePeriod.start());
  }
}
