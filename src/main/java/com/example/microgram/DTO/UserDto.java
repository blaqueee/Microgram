package com.example.microgram.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private String userName;
    private String name;
    private String email;
    private String password;
    private Integer posts;
    private Integer subscriptions;
    private Integer followers;
}
