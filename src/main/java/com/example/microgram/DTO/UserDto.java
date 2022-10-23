package com.example.microgram.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String email;
}
