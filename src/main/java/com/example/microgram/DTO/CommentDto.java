package com.example.microgram.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String text;
    private UserDto commentator;
    private LocalDateTime time;
}
