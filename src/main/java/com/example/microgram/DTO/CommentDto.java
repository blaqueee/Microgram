package com.example.microgram.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String text;
    private LocalDateTime time;

    @JsonProperty("post_id")
    private Long postID;
}
