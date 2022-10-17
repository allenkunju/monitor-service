package com.flightright.monitor.exception;

import org.springframework.http.HttpStatus;

public class FileNameIsRequiredRestException extends MonitorServiceRestException{

    public FileNameIsRequiredRestException() {
        super(HttpStatus.BAD_REQUEST.value(), "filename is required");
    }
}
