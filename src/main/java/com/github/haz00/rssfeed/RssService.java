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

    public void update(Long id) {
        Optional<Rss> found = repository.findById(id);

        if (found.isEmpty())
            throw new ApiException("RSS not found: " + id);

        // TODO async
        update(found.get());
    }

    public void updateAll() {
        log.info("update feeds");
        for (Rss s : repository.findAll()) {
            update(s);
        }
    }

    protected void update(Rss entity) {
        Objects.requireNonNull(entity);

        log.info("update feed {}", entity.getId());

        try {
            for (Item dto : reader.read(entity.getUrl()).toList()) {

                Article article = itemToArticle(dto, entity);

                // add new only
                // TODO optimize
                boolean contains = false;
                for (Article exist : entity.getItems()) {
                    if (Objects.equals(exist.getGuid(), article.getGuid())) {
                        // TODO update
                        contains = true;
                        break;
                    }
                }

                if (!contains)
                    entity.getItems().add(article);
            }

            entity.setLastUpdateMessage(null);

        } catch (Throwable t) {
            log.error(t.getMessage(), t);
            entity.setLastUpdateMessage(t.getMessage());
        }

        repository.save(entity);
    }

    protected Article itemToArticle(Item item, Rss entity) {
        Article a = new Article();
        a.setRss(entity);
        a.setGuid(item.getGuid().orElseThrow(() -> new IllegalStateException("Item must have GUID: " + entity.getUrl())));
        a.setLink(item.getLink().orElse(null));
        a.setDescription(item.getDescription().orElse(null));
        a.setTitle(item.getTitle().orElse(null));
        if (item.getPubDate().isPresent()) {
            try {
                String raw = item.getPubDate().get();
                a.setPubDate(LocalDateTime.parse(raw, DateTimeFormatter.RFC_1123_DATE_TIME));
            } catch (DateTimeParseException ignore) {
            }
        }
        return a;
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
