package com.example.microgram.Service;

import com.example.microgram.DAO.UserDao;
import com.example.microgram.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final UserDao userDao;

    public String createNewUser(User user) {
        return userDao.createNewUser(
                User.builder()
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build()
        );
    }
}
