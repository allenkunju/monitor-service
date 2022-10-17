package com.flightright.monitor.exception;

public class MonitorServiceRestException extends RuntimeException {

    private final int errorCode;
    private final String message;

    public MonitorServiceRestException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}