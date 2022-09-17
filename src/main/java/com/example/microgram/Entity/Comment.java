package com.example.microgram.Entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comment {
    private String text;
    private LocalDateTime time;

    public Comment(String text) {
        this.text = text;
        this.time = LocalDateTime.now();
    }
}
