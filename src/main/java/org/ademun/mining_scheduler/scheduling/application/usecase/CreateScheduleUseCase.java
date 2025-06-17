package org.ademun.mining_scheduler.scheduling.application.usecase;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.CreateScheduleCommand;
import org.ademun.mining_scheduler.scheduling.domain.model.Schedule;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleId;
import org.ademun.mining_scheduler.scheduling.domain.model.ScheduleRepository;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.CreateScheduleResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateScheduleUseCase implements
    UseCase<CreateScheduleCommand, CreateScheduleResponse> {

  private final ScheduleRepository scheduleRepository;

  @Override
  public CreateScheduleResponse execute(CreateScheduleCommand command) {
    Schedule schedule = Schedule.builder()
        .id(new ScheduleId(UUID.randomUUID()))
        .name(command.name())
        .build();
    Schedule savedSchedule = scheduleRepository.save(schedule);
    return new CreateScheduleResponse(savedSchedule.getId().value());
  }
}
