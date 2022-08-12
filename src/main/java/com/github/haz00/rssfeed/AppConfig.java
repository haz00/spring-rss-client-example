package com.github.haz00.rssfeed;

import com.apptasticsoftware.rssreader.RssReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RssReader rssReader() {
        return new RssReader();
    }
}
