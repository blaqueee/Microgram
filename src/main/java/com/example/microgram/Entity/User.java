package com.example.microgram.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    private String userName;
    private String name;
    private String email;
    private String password;
    private Integer posts;
    private Integer subscriptions;
    private Integer followers;
}
