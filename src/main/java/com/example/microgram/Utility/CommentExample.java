package com.example.microgram.Utility;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentExample {
    private Integer postID;
    private Integer userID;
    private String text;
    private LocalDateTime time;
}