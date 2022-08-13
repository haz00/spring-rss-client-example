package com.github.haz00.rssfeed;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "rss")
public class Rss {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(length = 512)
    private String lastUpdateMessage;

    @OneToMany(mappedBy = "rss", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Article> items;

    @Override
    public String toString() {
        return "RssEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", lastUpdateMessage='" + lastUpdateMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Rss rss = (Rss) o;
        return id != null && Objects.equals(id, rss.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
