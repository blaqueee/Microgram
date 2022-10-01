package com.example.microgram.Service;

import com.example.microgram.DAO.LikeDao;
import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.LikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LikeUserPostService {
    private final LikeDao likeDao;
    private final UserDao userDao;
    private final PostDao postDao;

    public String likePost(LikeDto likeDto, Authentication auth) {
        var username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "Вы должны авторизоваться, чтобы лайкать посты!";
        if (likeDao.ifUserLikedPost(userDao.getIdByUsername(username), likeDto.getPostId()))
            return "Вы уже лайкали этот пост!";
        if (!postDao.ifExistsId(likeDto.getPostId()))
            return "Такого поста не существует!";
        likeDto.setUserId(userDao.getIdByUsername(username));
        return likeDao.likePost(likeDto);
    }
}
