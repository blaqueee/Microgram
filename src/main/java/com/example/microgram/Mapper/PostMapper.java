package com.example.microgram.Mapper;

import com.example.microgram.DTO.Form.PostForm;
import com.example.microgram.DTO.PostDto;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class PostMapper {
    public PostForm toPostForm(MultipartFile image, String description, Long userId) {
        return PostForm.builder()
                .file(image)
                .description(description)
                .userId(userId)
                .build();
    }

    public PostDto toPostDto(KeyHolder keyHolder, String fileName, String description, LocalDateTime time) {
        return PostDto.builder()
                .id(Objects.requireNonNull(keyHolder.getKey()).longValue())
                .image(fileName)
                .description(description)
                .time(time)
                .comments(new ArrayList<>())
                .build();
    }
}
