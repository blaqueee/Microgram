package com.example.microgram.Service;

import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.UserDto;
import com.example.microgram.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final UserDao userDao;

    public UserDto getUserByName(String name) {
       return userDao.getUserByName(name);
//        return buildUserDto(user);
    }

    public UserDto getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
//        return buildUserDto(user);
    }

    public UserDto getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
//        return buildUserDto(user);
    }

    public String isRegistered(String email) {
        return userDao.isRegistered(email);
    }

    private UserDto buildUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .posts(user.getPosts())
                .subscriptions(user.getSubscriptions())
                .followers(user.getFollowers())
                .build();
    }
}
