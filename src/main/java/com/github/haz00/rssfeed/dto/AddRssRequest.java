package com.github.haz00.rssfeed.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddRssRequest implements Serializable {
    private String url;
}
