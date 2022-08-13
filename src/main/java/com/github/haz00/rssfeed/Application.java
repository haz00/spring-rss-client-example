package com.github.haz00.rssfeed;

import com.apptasticsoftware.rssreader.RssReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RssReader rssParser() {
        return new RssReader();
    }

    @Bean
    public RssUpdater rssUpdater() {
        return new RssUpdater();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setThreadNamePrefix("exec-rss-");
        exec.setKeepAliveSeconds(15);
        exec.setCorePoolSize(0);
        exec.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        exec.initialize();
        return exec;
    }
}
