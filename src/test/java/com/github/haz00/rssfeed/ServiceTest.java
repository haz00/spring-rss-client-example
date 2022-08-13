package com.github.haz00.rssfeed;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
class ServiceTest {

    @Autowired
    RssService service;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void createWithUrl() {
        Rss rss = service.createWithUrl("url");
        assertNotNull(rss.getId());
        assertEquals("url", rss.getUrl());
    }

    @Test
    void getRss() {
        Rss expect = service.createWithUrl("url");
        Rss actual = service.getRss(expect.getId());
        assertEquals(expect, actual);
    }

    @Test
    void removeFeed() {
        Rss rss = service.createWithUrl("url");
        service.removeRss(rss.getId());
        assertNull(service.getRss(rss.getId()));
    }

    @Test
    void updateAsync() throws ExecutionException, InterruptedException, TimeoutException {
        Rss rss = service.createWithUrl("https://lorem-rss.herokuapp.com/feed");

        Future<Long> future = service.updateAsync(rss.getId());
        Long id = future.get(5, TimeUnit.SECONDS);

        assertEquals(id, rss.getId());
        assertEquals(10, articleRepository.findByRssId(rss.getId()).size());
    }

    @Test
    void updateAllAsync() throws ExecutionException, InterruptedException, TimeoutException {
        Rss rss = service.createWithUrl("https://lorem-rss.herokuapp.com/feed");
        Rss rss2 = service.createWithUrl("https://lorem-rss.herokuapp.com/feed");

        Set<Long> expect = Set.of(rss.getId(), rss2.getId());
        Set<Long> actual = new HashSet<>();

        for (Future<Long> future : service.updateAllAsync()) {
            actual.add(future.get(5, TimeUnit.SECONDS));
        }

        assertEquals(expect, actual);
    }
}