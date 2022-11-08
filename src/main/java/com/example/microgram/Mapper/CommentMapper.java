package com.example.microgram.Mapper;

import com.example.microgram.DTO.CommentDto;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class CommentMapper {
    public CommentDto toCommentDto(KeyHolder kh, String text, LocalDateTime time) {
        return CommentDto.builder()
                .id(Objects.requireNonNull(kh.getKey()).longValue())
                .text(text)
                .time(time)
                .build();
    }
}
