package com.example.microgram.Entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {
    private Long id;
    private User follower;
    private User account;
    private LocalDateTime time = LocalDateTime.now();
}
