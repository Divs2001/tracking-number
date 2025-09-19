package com.teleport.parcel_tracking.exception;

import com.teleport.parcel_tracking.dto.TrackingNumberResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<TrackingNumberResponse>> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("Error Occurred: ", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TrackingNumberResponse(400,
                ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<TrackingNumberResponse>> handleGeneralError(Exception ex) {
        log.error("Error Occurred: ", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TrackingNumberResponse(500,
                "Some internal error occurred")));
    }
}

