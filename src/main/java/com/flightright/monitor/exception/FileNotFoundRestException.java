package com.flightright.monitor.exception;

import org.springframework.http.HttpStatus;

public class FileNotFoundRestException extends MonitorServiceRestException {
    public FileNotFoundRestException(String fileName) {
        super(HttpStatus.NOT_FOUND.value(), "File with the name " + fileName + " is not found");
    }
}
