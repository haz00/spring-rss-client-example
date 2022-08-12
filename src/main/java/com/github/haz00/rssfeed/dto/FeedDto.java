package com.github.haz00.rssfeed.dto;

import com.github.haz00.rssfeed.FeedPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FeedDto implements Serializable {

    private List<ArticleDto> articles;
    private int page;
    private int total;

    public static FeedDto fromFeed(FeedPage feed) {
        List<ArticleDto> articlesDto = feed.getArticles().stream()
                .map(ArticleDto::fromEntity)
                .toList();

        FeedDto feedDto = new FeedDto();
        feedDto.setArticles(articlesDto);
        feedDto.setPage(feed.getPage());
        feedDto.setTotal(feed.getTotal());
        return feedDto;
    }
}
