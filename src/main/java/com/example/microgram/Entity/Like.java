package com.example.microgram.Entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Like {
    private User liked;
    private Post likedPost;
    private LocalDateTime time;

    public Like(User liked, Post likedPost) {
        this.liked = liked;
        this.likedPost = likedPost;
        this.time = LocalDateTime.now();
    }
}
