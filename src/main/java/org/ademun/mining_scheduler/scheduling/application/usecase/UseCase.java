package org.ademun.mining_scheduler.scheduling.application.usecase;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface UseCase<C, R> {

  R execute(@Valid C command);
}
