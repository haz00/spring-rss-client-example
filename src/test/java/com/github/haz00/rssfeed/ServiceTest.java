package com.github.haz00.rssfeed;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceTest {

    @Autowired
    RssService service;

    @Test
    void addFeed() {
//        assertTrue(service.addRss("url"));
//        assertFalse(service.addRss("url"));
    }

    @Test
    void getFeed() {
//        assertNotNull(service.getRss("url"));
    }

    @Test
    void updateFeed() {
//        service.updateFeed("url");
    }

    @Test
    void removeFeed() {
//        assertTrue(service.removeRss("url"));
//        assertNull(service.getRss("url"));
    }
}