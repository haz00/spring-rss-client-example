package com.github.haz00.rssfeed;

import com.github.haz00.rssfeed.dto.AddRssRequest;
import com.github.haz00.rssfeed.dto.RssDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rss")
public class RssController {

    private RssService service;

    @PostMapping
    public ResponseEntity<RssDto> add(@RequestBody AddRssRequest data) {
        if (data.getUrl() == null || data.getUrl().isBlank())
            throw new ApiException("bad url: " + data.getUrl());

        Rss added = service.createByUrl(data.getUrl());
        return ResponseEntity.ok(RssDto.fromEntity(added));
    }

    @GetMapping
    public ResponseEntity<List<RssDto>> all() {
        return ResponseEntity.ok(RssDto.fromEntity(service.getAllRss()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id) {
        if (id < 0) throw new ApiException("bad id: " + id);

        service.updateAsync(id);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateAll() {
        service.updateAllAsync();
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        if (id < 0) throw new ApiException("bad id: " + id);
        service.removeRss(id);
        return ResponseEntity.ok().build();
    }

    @Autowired
    public void setService(RssService service) {
        this.service = service;
    }
}
