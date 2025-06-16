package org.ademun.mining_scheduler.scheduling.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recurring_event")
public class RecurringEvent extends Event {

}
