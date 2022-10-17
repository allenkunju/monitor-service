package com.flightright.monitor.exception;

import org.springframework.http.HttpStatus;

public class InternalServerRestException extends MonitorServiceRestException {
    public InternalServerRestException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }
}
