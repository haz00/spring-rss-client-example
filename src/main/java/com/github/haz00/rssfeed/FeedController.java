package com.github.haz00.rssfeed;

import com.github.haz00.rssfeed.dto.FeedDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private FeedService service;

    @GetMapping
    public ResponseEntity<FeedDto> all(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "2") int length) {
        if (length > 100 || length < 0)
            throw new ApiException("bad length: " + length);

        if (page < 0)
            throw new ApiException("bad page: " + page);

        FeedPage feed = service.getFeed(page, length);
        return ResponseEntity.ok(FeedDto.fromFeed(feed));
    }

    @Autowired
    public void setService(FeedService service) {
        this.service = service;
    }
}
