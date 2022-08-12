package com.github.haz00.rssfeed;

import com.github.haz00.rssfeed.dto.AddRssRequest;
import com.github.haz00.rssfeed.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/feed")
public class RssController {

    private RssService service;

    @PostMapping
    public ResponseEntity<?> addFeed(@RequestBody AddRssRequest data) {
        RssEntity added = service.addRss(data.getUrl());
        return ResponseEntity.ok(added);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RssEntity> getFeed(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRss(id));
    }

    @GetMapping
    public ResponseEntity<List<RssEntity>> getFeeds() {
        return ResponseEntity.ok(service.getAllRss());
    }

    @PostMapping("/update/{url}")
    public ResponseEntity<?> updateFeed(@PathVariable Long id) throws Throwable {
        service.updateFeed(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateFeeds() throws Throwable {
        service.updateFeeds();
        return ResponseEntity.ok(null);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> exceptionHandler(Throwable e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(ApiError.of(e.getMessage()));
    }

    @Autowired
    public void setService(RssService service) {
        this.service = service;
    }
}
