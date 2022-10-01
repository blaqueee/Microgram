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
        if (userDto.getUsername() == null)
            return "Пустое имя пользователя!";
        if (userDto.getName() == null)
            return "Пустое имя!";
        if (userDto.getEmail() == null)
            return "Нету электронной почты!";
        if (userDto.getPassword() == null)
            return "Регистрация без пароли невозможна!";

        return userDao.createNewUser(
                User.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build()
        );
    }
}
