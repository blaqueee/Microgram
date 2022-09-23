package com.example.microgram.DataGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LikeExample {
    private Integer userID;
    private Integer postID;
    private LocalDateTime time;
}
