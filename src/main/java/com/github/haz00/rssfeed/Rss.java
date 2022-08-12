package com.github.haz00.rssfeed;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "rss")
public class Rss {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(length = 512)
    private String lastUpdateMessage;

    @OneToMany(mappedBy = "rss", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> items;

    public Rss() {
    }

    @Override
    public String toString() {
        return "RssEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", lastUpdateMessage='" + lastUpdateMessage + '\'' +
                '}';
    }
}
