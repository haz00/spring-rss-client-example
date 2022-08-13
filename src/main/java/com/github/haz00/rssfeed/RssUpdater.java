package com.github.haz00.rssfeed;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * A proxy class, that makes possible to spawn a thread on each update() call within the same service
 */
@Slf4j
public class RssUpdater {

    private RssReader reader;

    private RssRepository rssRepository;
    private ArticleRepository articleRepository;

    @Async
    @Transactional
    public Future<Long> updateAsync(Long id) {
        Rss rss = rssRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("rss not found: " + id));

        log.info("update rss {}", id);

        try {
//            Thread.sleep(2000);
            for (Item dto : reader.read(rss.getUrl()).toList()) {

                Article article = itemToArticle(dto, rss);

                // filter existing to add new only, update otherwise
                // TODO optimize
                boolean contains = false;
                for (Article exist : rss.getItems()) {
                    if (Objects.equals(exist.getGuid(), article.getGuid())) {
                        updateArticle(exist, article);
                        contains = true;
                        break;
                    }
                }

                if (!contains)
                    rss.getItems().add(article);
            }

            rss.setLastUpdateMessage(null);
            log.info("feed updated {}", rss.getId());

        } catch (Throwable e) {
            rss.setLastUpdateMessage(e.getMessage());
            log.error(e.getMessage(), e);
        }
        rssRepository.save(rss);

        return new AsyncResult<>(id);
    }

    protected Article itemToArticle(Item item, Rss entity) {
        Objects.requireNonNull(item);
        Objects.requireNonNull(entity);

        Article a = new Article();
        a.setGuid(item.getGuid().orElseThrow(() -> new IllegalStateException("Item must have GUID: " + entity.getUrl())));
        a.setRss(entity);
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

    protected void updateArticle(Article oldValue, Article newValue) {
        oldValue.setDescription(newValue.getDescription());
        oldValue.setLink(newValue.getLink());
        oldValue.setPubDate(newValue.getPubDate());
        oldValue.setTitle(newValue.getTitle());
        articleRepository.save(oldValue);
    }

    @Autowired
    public void setReader(RssReader reader) {
        this.reader = reader;
    }

    @Autowired
    public void setRssRepository(RssRepository rssRepository) {
        this.rssRepository = rssRepository;
    }

    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
}
