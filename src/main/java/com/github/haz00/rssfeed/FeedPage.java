package com.github.haz00.rssfeed;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FeedPage {
    private List<Article> articles;
    private int page;
    private int total;
}
