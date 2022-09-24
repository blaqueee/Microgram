package com.example.microgram.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private String username;
    private String name;
    private String email;
    private String password;
    private int posts;
    private int subscriptions;
    private int followers;
}
