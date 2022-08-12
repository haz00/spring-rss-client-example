package com.github.haz00.rssfeed.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FeedRequest implements Serializable {
    private int page;
    private int length;
}
