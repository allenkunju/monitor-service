package com.flightright.monitor.application.service;

import com.flightright.monitor.application.model.Report;
import com.flightright.monitor.application.model.VisitingEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MonitorServiceTest {

    @Autowired
    MonitorService monitorService;

    @Test
    void processCsvReport() {
        Report report = monitorService.processCsvReport("sample_100.csv");
        assertEquals(report.getUniqueHits(), 100);
    }

    @Test
    void getValidEntry() {
        VisitingEntry validEntry = monitorService.getValidEntry("test@test.com,123,google.com");
        assertEquals("test@test.com,123,google.com \n", validEntry.toString());
    }
    @Test
    void getValidEntryWithMissingValues() {
        VisitingEntry validEntry = monitorService.getValidEntry("test@test.com,,google.com");
        assertNull(validEntry);
    }
    @Test
    void getValidEntryWithEmptyEntry() {
        VisitingEntry validEntry = monitorService.getValidEntry("");
        assertNull(validEntry);
    }
}