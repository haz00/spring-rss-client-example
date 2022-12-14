package com.github.haz00.rssfeed;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

    List<Article> findByRssId(Long id);
}
