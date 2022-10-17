package com.flightright.monitor.config;

import com.flightright.monitor.application.model.ErrorResponse;
import com.flightright.monitor.exception.MonitorServiceRestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandlers {

    @ExceptionHandler(value = {MonitorServiceRestException.class})
    public ResponseEntity<ErrorResponse> handleServiceRestException(final MonitorServiceRestException exception) {
        return ResponseEntity
            .status(exception.getErrorCode())
            .body(new ErrorResponse(exception.getMessage(), exception.getErrorCode()));
    }
}
