package com.github.haz00.rssfeed.dto;

import com.github.haz00.rssfeed.Rss;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RssDto implements Serializable {
    private Long id;
    private String url;

    public static RssDto fromEntity(Rss entity) {
        RssDto dto = new RssDto();
        dto.setId(entity.getId());
        dto.setUrl(entity.getUrl());
        return dto;
    }

    public static List<RssDto> fromEntity(List<Rss> entities) {
        return entities.stream()
                .map(RssDto::fromEntity)
                .toList();
    }
}
