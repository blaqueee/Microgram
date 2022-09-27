package com.example.microgram.Service;

import com.example.microgram.DAO.SubscriptionDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.SubscriptionDto;
import com.example.microgram.Utility.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class SubscriptionUserService {
    private final SubscriptionDao subDao;
    private final UserDao userDao;

    public String follow(SubscriptionDto subDto, HttpServletRequest request) {
        var username = CookieUtil.getUsernameFromCookie(request);
        if (username.isEmpty() || !userDao.ifExistsUsername(username.get()))
            return "Вы должны авторизоваться, чтобы подписываться на других пользователей!";
        if (!userDao.ifExistsId(subDto.getUserId()))
            return "Такого пользователя не существует!";
        if (subDto.getUserId().equals(userDao.getIdByUsername(username.get())))
            return "Вы не можете подписываться на себя!";
        return subDao.follow(subDto.getUserId(), userDao.getIdByUsername(username.get()));
    }
}
