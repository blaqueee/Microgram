package com.example.microgram.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostUserDto {
    private Long id;
    private String image;
    private String description;
    private String username;
}