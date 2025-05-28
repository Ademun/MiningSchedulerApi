package org.ademun.mining_scheduler.dto.response;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus code, String message) {

}
