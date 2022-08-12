package com.github.haz00.rssfeed;

import com.github.haz00.rssfeed.dto.FeedDto;
import com.github.haz00.rssfeed.dto.FeedRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private FeedService service;

    @GetMapping
    public ResponseEntity<FeedDto> all(@RequestBody FeedRequest data) {
        if (data.getLength() > 100 || data.getLength() < 0)
            throw new ApiException("bad length: " + data.getLength());

        if (data.getPage() < 0)
            throw new ApiException("bad page: " + data.getPage());

        FeedPage feed = service.getFeed(data.getPage(), data.getLength());
        return ResponseEntity.ok(FeedDto.fromFeed(feed));
    }

    @Autowired
    public void setService(FeedService service) {
        this.service = service;
    }
}
