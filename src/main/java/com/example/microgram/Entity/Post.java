package com.example.microgram.Entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Post {
    private String image;
    private String description;
    private LocalDateTime time;
    private List<Comment> comments;

    public Post(String image, String description) {
        this.image = image;
        this.description = description;
        this.time = LocalDateTime.now();
        this.comments = new ArrayList<>();
    }
}
