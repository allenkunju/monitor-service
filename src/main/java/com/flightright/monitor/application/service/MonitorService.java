package com.flightright.monitor.application.service;

import com.flightright.monitor.application.model.Report;
import com.flightright.monitor.application.model.VisitingEntry;
import com.flightright.monitor.cache.InMemoryUserKeyCache;
import com.flightright.monitor.exception.FileNameIsRequiredRestException;
import com.flightright.monitor.exception.FileNotFoundRestException;
import com.flightright.monitor.exception.InternalServerRestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service to monitor the visitors to website
 */
@Service
public class MonitorService {

    private final InMemoryUserKeyCache cache;

    public MonitorService(InMemoryUserKeyCache cache) {
        this.cache = cache;
    }

    /**
     * This method reads the input csv file as a stream,
     *  filters valid entries,
     *  checks and adds unique entries to cache,
     *  and writes it to and output file.
     *
     * @param fileName the file should be available under the resources
     * @return Report containing unique entry count
     */
    public Report processCsvReport(String fileName) {

        if (!StringUtils.hasLength(fileName)) {
            throw new FileNameIsRequiredRestException();
        }
        AtomicInteger visitCount = new AtomicInteger();

        URL url = this.getClass().getClassLoader().getResource("files/" + fileName);
        if (url == null) {
            throw new FileNotFoundRestException(fileName);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(url.getFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter("out_" + fileName))
        ) {
            reader.lines().map(this::getValidEntry) // parses the string to ValidEntry object
                    .filter(Objects::nonNull) // filters invalid entries
                    .filter(visit -> !cache.contains(visit.key())) //checking duplicates for the unique key
                    .forEach(uniqueVisit -> {
                        visitCount.getAndIncrement();
                        cache.add(uniqueVisit.key());
                        logOutPut(writer, uniqueVisit);
                    });
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new InternalServerRestException("Error while reading the file");
        } finally {
            cache.clear();
        }
        return new Report(visitCount.get());
    }

    /**
     * logs the unique result to another csv file
     * @param writer
     * @param uniqueVisit
     */
    private static void logOutPut(BufferedWriter writer, VisitingEntry uniqueVisit) {
        try {
            writer.write(uniqueVisit.toString());
        } catch (IOException e) {
            throw new InternalServerRestException("Error while logging the results");
        }
    }

    /**
     * validates the visit entries and returns only valid entries
     * @param entry line from input csv file. <br>
     *             Expected format : '&lt;email&gt;,&lt;phone&gt;,&lt;source&gt;'
     * @return null - if the entry is corrupted, or with missing values
     */
    protected VisitingEntry getValidEntry(String entry) {
       if (!StringUtils.hasLength(entry) || entry.startsWith("email")) { //exclude empty lines and header line
            return null;
        }
        String[] parts = entry.split(",");
        if (parts.length == 3 && StringUtils.hasLength(parts[0])
                && StringUtils.hasLength(parts[1]) && StringUtils.hasLength(parts[2])) {
            return new VisitingEntry(parts[0], parts[1], parts[2]);
        }
        // return null if any value is missing in the line
        return null;
    }
}
