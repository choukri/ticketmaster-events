package com.ticketmaster.events.infrastructure.datasource;


import com.ticketmaster.events.domain.enumeration.Status;
import com.ticketmaster.events.domain.model.Event;
import com.ticketmaster.events.domain.model.Seat;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class CSVDataLoader {

    @Value("${events.file.batch.size}")
    private int batchSize;

    @Value("${events.file.path}")
    private String filePath;


    @Value("${events.file.split}")
    private String splitCharacter;
    private final Map<Long, Event> eventMap = new ConcurrentHashMap<>();


    @PostConstruct
    public void loadEventDataInMemoryOnStartup() {
        // Run the CSV processing in a separate thread to avoid blocking startup
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorService.submit(this::processCsvFileEvent);
        executorService.shutdown();

    }

    public Map<Long, Event> getEventMap() {
        return eventMap;
    }

    private void processCsvFileEvent() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<String[]> chunk = new ArrayList<>();
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                chunk.add(line.split(splitCharacter));
                if (chunk.size() >= batchSize) {
                    processChunkEvent(chunk);
                    chunk.clear();
                }
            }
            // Process the remaining chunk
            if (!chunk.isEmpty()) {
                processChunkEvent(chunk);
            }
        } catch (IOException e) {
            log.error("error lors de parsing file ",e);
        }
    }

    private void processChunkEvent(List<String[]> chunk) {
        // Run the processing of each chunk in parallel
        ExecutorService chunkExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (String[] line : chunk) {
            chunkExecutor.submit(() -> processLineEvent(line));
        }
        chunkExecutor.shutdown();
    }

    private void processLineEvent(String[] line) {

        long eventId = Long.parseLong(line[0].trim());
        long seatNumber = Long.parseLong(line[1].trim());
        long row = Long.parseLong(line[2].trim());
        String level = line[3].trim().toUpperCase();
        String section = line[4].trim().toUpperCase();
        Status status = Status.fromString(line[5].trim());
        LocalDateTime eventDate = LocalDateTime.parse(line[6], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        long sellRank = Long.parseLong(line[7].trim());
        boolean hasUpsells = Boolean.parseBoolean(line[8].trim());

        Seat seat = new Seat(seatNumber, row, level, section, status, sellRank, hasUpsells);

        // Add the seat to  event
        Event event = eventMap.computeIfAbsent(eventId, id -> new Event(eventId, "Event " + eventId, eventDate, new ArrayList<>()));

        event.getSeats().add(seat);
    }
}


