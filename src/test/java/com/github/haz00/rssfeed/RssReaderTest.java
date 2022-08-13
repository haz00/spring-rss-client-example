package com.github.haz00.rssfeed;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RssReaderTest {

    @Test
    public void read() throws IOException {
        try (InputStream src = RssReaderTest.class.getResourceAsStream("/feed")) {
            RssReader reader = new RssReader();
            Stream<Item> rssFeed = reader.read(src);
            List<Item> articles = rssFeed.collect(Collectors.toList());

            assertEquals(10, articles.size());

            Item first = articles.get(0);

            assertEquals(of("Lorem ipsum 2022-08-11T10:01:00Z"), first.getTitle());
            assertEquals(of("Aliquip ad elit mollit ipsum aliqua tempor nostrud esse."), first.getDescription());
            assertEquals(of("http://example.com/test/1660212060"), first.getLink());
            assertEquals(of("http://example.com/test/1660212060"), first.getGuid());
            assertEquals(of(true), first.getIsPermaLink());
//            assertEquals(of("John Smith"), first.getAuthor()); FIXME library has no `creator` field
            assertEquals(of("Thu, 11 Aug 2022 10:01:00 GMT"), first.getPubDate());
        }
    }
}
