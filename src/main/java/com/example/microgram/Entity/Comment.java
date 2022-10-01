package com.example.microgram.Entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Comment {
    private String text;
    private LocalDateTime time;
}
