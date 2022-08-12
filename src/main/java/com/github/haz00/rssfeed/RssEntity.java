package com.github.haz00.rssfeed;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "rss")
public class RssEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String url;

    private String lastUpdateMessage;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, targetEntity = ItemEntity.class)
    private List<ItemEntity> items;

    public RssEntity() {
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
