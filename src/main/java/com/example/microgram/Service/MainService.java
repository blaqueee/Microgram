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

    public String createNewUser(UserDto userDto) {
        return userDao.createNewUser(
                User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build()
        );
    }

    public String loginByUsername(UserDto userDto) {
        return userDao.loginByUsername(
                User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build()
        );
    }
}