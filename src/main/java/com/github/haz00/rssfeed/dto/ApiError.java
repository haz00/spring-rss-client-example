package com.github.haz00.rssfeed.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiError {
    private List<String> messages;

    public static ApiError of(String...messages) {
        ApiError e = new ApiError();
        e.setMessages(List.of(messages));
        return e;
    }
}
