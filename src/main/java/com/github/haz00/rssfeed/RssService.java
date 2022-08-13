package com.github.haz00.rssfeed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
public class RssService {

    private RssRepository repository;
    private RssUpdater updater;

    @Scheduled(fixedDelayString = "${rssUpdateSeconds}", timeUnit = TimeUnit.SECONDS)
    public void rssScheduler() {
        log.info("rss scheduler awake");
        updateAllAsync();
    }

    public Rss createByUrl(String url) {
        requireNonNull(url);

        Rss exist = repository.findByUrl(url);

        if (exist != null)
            throw new ApiException("URL already exists: " + url);

        Rss newRss = new Rss();
        newRss.setUrl(url);

        log.info("add rss {}", newRss);
        return repository.save(newRss);
    }

    public Rss getRss(Long id) {
        requireNonNull(id);
        return repository.findById(id).orElseThrow();
    }

    public List<Rss> getAllRss() {
        return repository.findAll();
    }

    public void removeRss(Long id) {
        requireNonNull(id);
        log.info("remove rss {}", id);
        repository.deleteById(id);
    }

    public Future<Long> updateAsync(Long id) {
        return updater.updateAsync(id);
    }

    public List<Future<Long>> updateAllAsync() {
        log.info("update feeds");

        return repository.findAll().stream()
                .map(rss -> updater.updateAsync(rss.getId()))
                .toList();
    }

    @Autowired
    public void setRepository(RssRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setUpdater(RssUpdater updater) {
        this.updater = updater;
    }
}
