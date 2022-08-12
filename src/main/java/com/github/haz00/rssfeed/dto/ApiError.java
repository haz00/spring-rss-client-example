package com.github.haz00.rssfeed.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiError implements Serializable {
    private String message;

    public static ApiError of(String message) {
        ApiError e = new ApiError();
        e.setMessage(message);
        return e;
    }
}
