package com.example.microgram.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private String userName;
    private String email;
    private String password;
    private List<Post> posts;
    private List<User> subscriptions;
    private List<User> followers;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.posts = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.followers = new ArrayList<>();
    }
}
