package com.github.haz00.rssfeed;

import com.apptasticsoftware.rssreader.RssReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RssReader rssParser() {
		return new RssReader();
	}
}
