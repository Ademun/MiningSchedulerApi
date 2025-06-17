package org.ademun.mining_scheduler.scheduling.interfaces.rest.dto.response;

import java.io.Serializable;
import java.util.UUID;

public record AddEventResponse(UUID id) implements
    Serializable {

}
