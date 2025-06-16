package org.ademun.mining_scheduler.scheduling.interfaces.rest.controller;

import jakarta.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ademun.mining_scheduler.scheduling.application.usecase.AddEventUseCase;
import org.ademun.mining_scheduler.scheduling.application.usecase.AddWeekUseCase;
import org.ademun.mining_scheduler.scheduling.application.usecase.CreateScheduleUseCase;
import org.ademun.mining_scheduler.scheduling.application.usecase.DeleteScheduleUseCase;
import org.ademun.mining_scheduler.scheduling.application.usecase.GetEventsUseCase;
import org.ademun.mining_scheduler.scheduling.application.usecase.GetScheduleUseCase;
import org.ademun.mining_scheduler.scheduling.application.usecase.RemoveEventUseCase;
import org.ademun.mining_scheduler.scheduling.application.usecase.RemoveWeekUseCase;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.AddEventCommand;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.CreateScheduleCommand;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.GetEventsCommand;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.RemoveEventCommand;
import org.ademun.mining_scheduler.scheduling.application.usecase.command.RemoveWeekCommand;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.request.AddEventRequest;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.request.CreateScheduleRequest;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.CreateScheduleResponse;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetEventResponse;
import org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response.GetScheduleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

  private final CreateScheduleUseCase createScheduleUseCase;
  private final GetScheduleUseCase getScheduleUseCase;
  private final AddWeekUseCase addWeekUseCase;
  private final RemoveWeekUseCase removeWeekUseCase;
  private final GetEventsUseCase getEventsUseCase;
  private final AddEventUseCase addEventUseCase;
  private final RemoveEventUseCase removeEventUseCase;
  private final DeleteScheduleUseCase deleteScheduleUseCase;

  @PostMapping
  public ResponseEntity<CreateScheduleResponse> createSchedule(
      @RequestBody @Valid CreateScheduleRequest request) {
    CreateScheduleCommand command = new CreateScheduleCommand(request.name());
    CreateScheduleResponse createdSchedule = createScheduleUseCase.execute(command);
    return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
  }

  @GetMapping("/{scheduleId}")
  public ResponseEntity<GetScheduleResponse> getSchedule(@PathVariable UUID scheduleId) {
    GetScheduleResponse schedule = getScheduleUseCase.execute(scheduleId);
    return ResponseEntity.ok(schedule);
  }

  @PatchMapping("/{scheduleId}")
  public ResponseEntity<Void> addWeek(@PathVariable UUID scheduleId) {
    addWeekUseCase.execute(scheduleId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{scheduleId}/weeks/{weekIndex}")
  public ResponseEntity<Void> removeWeek(@PathVariable UUID scheduleId,
      @PathVariable Integer weekIndex) {
    RemoveWeekCommand command = new RemoveWeekCommand(scheduleId, weekIndex);
    removeWeekUseCase.execute(command);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{scheduleId}/weeks/{weekIndex}/days/{dayOfWeek}/events")
  public ResponseEntity<List<GetEventResponse>> getEvents(@PathVariable UUID scheduleId,
      @PathVariable Integer weekIndex, @PathVariable Integer dayOfWeek) {
    GetEventsCommand command = new GetEventsCommand(scheduleId, weekIndex, DayOfWeek.of(dayOfWeek));
    List<GetEventResponse> events = getEventsUseCase.execute(command);
    return ResponseEntity.ok(events);
  }

  @PostMapping("/{scheduleId}/events")
  public ResponseEntity<Void> addEvent(@PathVariable UUID scheduleId,
      @RequestBody @Valid AddEventRequest request) {
    AddEventCommand command = new AddEventCommand(scheduleId, request.title(),
        request.description(), request.start(), request.end(), request.isTemporary(),
        request.date(), request.weekIndex(), request.dayOfWeek());
    addEventUseCase.execute(command);
    return ResponseEntity.noContent().build();
  }


  @DeleteMapping("/{scheduleId}/events/{eventId}")
  public ResponseEntity<Void> removeEvent(@PathVariable UUID scheduleId,
      @PathVariable UUID eventId) {
    RemoveEventCommand command = new RemoveEventCommand(scheduleId, eventId);
    removeEventUseCase.execute(command);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{scheduleId}")
  public ResponseEntity<Void> deleteSchedule(@PathVariable UUID scheduleId) {
    deleteScheduleUseCase.execute(scheduleId);
    return ResponseEntity.noContent().build();
  }
}
