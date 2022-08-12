package com.github.haz00.rssfeed;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
public class RssService {

    private RssRepository repository;
    private RssReader reader;

    public RssEntity addRss(String url) {
        requireNonNull(url);

        RssEntity exist = repository.findByUrl(url);

        if (exist != null)
            throw new IllegalStateException("URL already exists: " + url);

        RssEntity newRss = new RssEntity();
        newRss.setUrl(url);

        log.info("add rss {}", newRss);
        return repository.save(newRss);
    }

    public RssEntity getRss(Long id) {
        requireNonNull(id);
        return repository.findById(id).orElseThrow();
    }

    public List<RssEntity> getAllRss() {
        return repository.findAll();
    }

    public void removeRss(Long id) {
        requireNonNull(id);
        log.info("remove rss {}", id);
        repository.deleteById(id);
    }

    public void updateFeed(Long id) throws Throwable {
        Optional<RssEntity> found = repository.findById(id);

        if (found.isEmpty())
            throw new IllegalStateException("RSS not found: " + id);

        // TODO async
        updateFeed(found.get());
    }

    public void updateFeeds() throws Throwable {
        log.info("update feeds");
        for (RssEntity s : repository.findAll()) {
            updateFeed(s);
        }
    }

    protected void updateFeed(RssEntity entity) throws Throwable {
        Objects.requireNonNull(entity);

        log.info("update feed {}", entity.getId());

        try {
            for (Item dto : reader.read(entity.getUrl()).toList()) {

                ItemEntity item = mapToEntity(dto, entity);

                // add new only
                // TODO optimize
                boolean contains = false;
                for (ItemEntity exist : entity.getItems()) {
                    if (Objects.equals(exist.getGuid(), item.getGuid())) {
                        contains = true;
                        break;
                    }
                }

                if (!contains)
                    entity.getItems().add(item);
            }

            entity.setLastUpdateMessage(null);

        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            entity.setLastUpdateMessage(t.getMessage());
        }

        repository.save(entity);
    }

    protected ItemEntity mapToEntity(Item item, RssEntity entity) {
        ItemEntity e = new ItemEntity();
        e.setOwner(entity);
        e.setGuid(item.getGuid().orElseThrow(() -> new IllegalStateException("Item must have GUID: " + entity.getUrl())));
        e.setLink(item.getLink().orElse(null));
        e.setDescription(item.getDescription().orElse(null));
        e.setTitle(item.getTitle().orElse(null));
        if (item.getPubDate().isPresent()) {
            try {
                LocalDateTime date = LocalDateTime.parse(item.getPubDate().get(), DateTimeFormatter.RFC_1123_DATE_TIME);
                e.setPubDate(date);
            } catch (DateTimeParseException ignore) {
            }
        }
        return e;
    }

    @Autowired
    public void setRepository(RssRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setReader(RssReader reader) {
        this.reader = reader;
    }
}
