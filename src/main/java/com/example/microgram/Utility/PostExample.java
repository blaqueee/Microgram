package com.example.microgram.Utility;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostExample {
    private String image;
    private String description;
    private LocalDateTime time;
    private Integer userID;
}
