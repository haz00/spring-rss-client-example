package com.github.haz00.rssfeed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RssRepository extends JpaRepository<Rss, Long> {

    Rss findByUrl(@Param("url") String url);
}
