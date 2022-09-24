package com.example.microgram.Service;

import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.UserDto;
import com.example.microgram.Entity.User;
import com.example.microgram.Utility.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

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

    public String loginByUsername(UserDto userDto, HttpServletResponse response) {
        var isAuthorized = userDao.loginByUsername(
                User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build()
        );

        if (isAuthorized) {
            CookieUtil.addCookie(userDto, response);
            return "Вы успешно авторизовались!";
        }
        return "Неверное имя пользователя или пароль!";
    }
}
