package org.ademun.mining_scheduler.scheduling.application.usecase;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteScheduleUseCase implements
    UseCase<UUID, Void> {

  private final ScheduleRepository scheduleRepository;

  @Override
  public Void execute(UUID scheduleId) {
    scheduleRepository.deleteById(new ScheduleId(scheduleId));
    return null;
  }
}
