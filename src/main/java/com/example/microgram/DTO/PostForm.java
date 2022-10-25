package com.example.microgram.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostForm {
    @JsonProperty("user_id")
    private Long userId;
    private MultipartFile file;
    private String description;
}
