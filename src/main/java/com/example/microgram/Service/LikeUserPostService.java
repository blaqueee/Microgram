package com.example.microgram.Service;

import com.example.microgram.DAO.LikeDao;
import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.LikeDto;
import com.example.microgram.Utility.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class LikeUserPostService {
    private final LikeDao likeDao;
    private final UserDao userDao;
    private final PostDao postDao;

    public String likePost(LikeDto likeDto, HttpServletRequest request) {
        var username = CookieUtil.getUsernameFromCookie(request);
        if (username.isEmpty() || !userDao.ifExistsUsername(username.get()))
            return "Вы должны авторизоваться, чтобы лайкать посты!";
        if (likeDao.ifUserLikedPost(userDao.getIdByUsername(username.get()), likeDto.getPostId()))
            return "Вы уже лайкали этот пост!";
        if (!postDao.ifExistsId(likeDto.getPostId()))
            return "Такого поста не существует!";
        likeDto.setUserId(userDao.getIdByUsername(username.get()));
        return likeDao.likePost(likeDto);
    }
}
