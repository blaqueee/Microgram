package com.example.microgram.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PostDto {
    private String image;
    private String description;
    private LocalDateTime time;
    private Integer comments;
}
