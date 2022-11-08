package com.example.microgram.Entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private Long id;
    private String text;
    private LocalDateTime time = LocalDateTime.now();
}
