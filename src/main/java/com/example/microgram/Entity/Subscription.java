package com.example.microgram.Entity;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Subscription {
    private User follower;
    private User account;
    private LocalDateTime time;

    public Subscription(User follower, User account) {
        this.follower = follower;
        this.account = account;
        this.time = LocalDateTime.now();
    }
}
