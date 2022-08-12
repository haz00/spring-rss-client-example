package com.github.haz00.rssfeed;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String guid;
    private String title;
    private String description;
    private String link;
    private LocalDateTime pubDate;

    @JsonBackReference
    @ManyToOne
    private RssEntity owner;

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id='" + id + '\'' +
                ", guid='" + guid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", pubDate=" + pubDate +
                '}';
    }
}
