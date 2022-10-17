package com.flightright.monitor.application.model;

public class Report {

    private final int uniqueHits;

    public Report(int uniqueHits) {
        this.uniqueHits = uniqueHits;
    }

    public int getUniqueHits() {
        return uniqueHits;
    }
}
