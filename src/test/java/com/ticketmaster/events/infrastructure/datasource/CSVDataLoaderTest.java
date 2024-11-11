package com.ticketmaster.events.infrastructure.datasource;

import com.ticketmaster.events.domain.model.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CSVDataLoaderTest {

    @Autowired
    private CSVDataLoader csvDataLoader;

    @Test
    void testLoadEventDataInMemoryOnStartup() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<Long, Event> eventMap = csvDataLoader.getEventMap();
        assertNotNull(eventMap);
        assertTrue(eventMap.size() > 0);
        Event event = eventMap.get(1L);
        assertNotNull(event);
        assertFalse(event.getSeats().isEmpty());
    }
}
