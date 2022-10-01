package com.example.microgram.Service;

import com.example.microgram.DAO.SubscriptionDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SubscriptionUserService {
    private final SubscriptionDao subDao;
    private final UserDao userDao;

    public String follow(SubscriptionDto subDto, Authentication auth) {
        var username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "Вы должны авторизоваться, чтобы подписываться на других пользователей!";
        if (!userDao.ifExistsId(subDto.getUserId()))
            return "Такого пользователя не существует!";
        if (subDto.getUserId().equals(userDao.getIdByUsername(username)))
            return "Вы не можете подписываться на себя!";
        return subDao.follow(subDto.getUserId(), userDao.getIdByUsername(username));
    }
}
