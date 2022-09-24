package com.example.microgram.Utility;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SubscriptionExample {
    private Integer userID;
    private Integer followerID;
    private LocalDateTime time;
}
