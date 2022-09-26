package com.example.microgram.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PostUserImageDto {
    private MultipartFile imageFile;
    private String description;
    private String username;
}
