package com.example.microgram.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String password;
    private Integer posts;
    private Integer subscriptions;
    private Integer followers;
}
