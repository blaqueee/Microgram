package com.example.microgram.Entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private Long id;
    private String image;
    private String description;
    private LocalDateTime time;
    private List<Comment> comments = new ArrayList<>();

    public Post(String image, String description) {
        this.image = image;
        this.description = description;
        this.time = LocalDateTime.now();
    }
}
