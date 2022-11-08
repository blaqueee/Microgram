package com.example.microgram.Entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private Long id;
    private String image;
    private String description;
    private LocalDateTime time = LocalDateTime.now();
    private List<Comment> comments = new ArrayList<>();
}
