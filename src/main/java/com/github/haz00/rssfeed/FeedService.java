package com.github.haz00.rssfeed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FeedService {

    private ArticleRepository articleRepository;

    public FeedPage getFeed(int page, int len) {
        PageRequest req = PageRequest.of(page, len, Sort.by("pubDate").descending());
        Page<Article> articles = articleRepository.findAll(req);

        return FeedPage.builder()
                .page(page)
                .total(articles.getTotalPages())
                .articles(articles.stream().toList())
                .build();
    }

    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
}
