package com.github.haz00.rssfeed.dto;

import com.github.haz00.rssfeed.Article;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ArticleDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String link;
    private LocalDateTime pubDate;

    public static ArticleDto fromEntity(Article article) {
        ArticleDto dto = new ArticleDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setDescription(article.getDescription());
        dto.setLink(article.getLink());
        dto.setPubDate(article.getPubDate());
        return dto;
    }
}
