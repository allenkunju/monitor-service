package com.flightright.monitor.application.model;

public class VisitingEntry {
    private final String email;
    private final String phone;
    private final String source;

    public VisitingEntry(String email, String phone, String source) {
        this.email = email;
        this.phone = phone;
        this.source = source;
    }

    public String key() {
        return (email + phone);
    }

    @Override
    public String toString() {
        return email + "," + phone + "," + source + " \n";
    }
}