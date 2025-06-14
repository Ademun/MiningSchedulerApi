package org.ademun.mining_scheduler.scheduling.infrastructure.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfig {

  @Bean
  public Clock systemClock() {
    return Clock.systemDefaultZone();
  }
}
