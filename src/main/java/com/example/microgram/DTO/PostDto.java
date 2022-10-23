package com.example.microgram.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String image;
    private String description;
    private LocalDateTime time;
    private UserDto poster;
    private List<CommentDto> comments = new ArrayList<>();
}
