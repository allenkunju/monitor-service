package com.flightright.monitor.application;

import com.flightright.monitor.application.model.Report;
import com.flightright.monitor.application.service.MonitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    private final MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping(value = "/csv-report", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Report getReportFromCsv(@RequestParam("filename") String filename) {
        return monitorService.processCsvReport(filename);
    }

}
