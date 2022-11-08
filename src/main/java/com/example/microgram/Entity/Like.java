package com.example.microgram.Entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    private Long id;
    private User liked;
    private Post likedPost;
    private LocalDateTime time = LocalDateTime.now();
}
