package com.example.microgram.Service;

import com.example.microgram.DAO.LikeDao;
import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.Form.LikeForm;
import com.example.microgram.DTO.LikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LikeUserPostService {
    private final LikeDao likeDao;
    private final UserDao userDao;
    private final PostDao postDao;

    public ResponseEntity<?> likePost(LikeForm likeForm, Authentication auth) {
        var username = auth.getName();
        if (likeDao.ifUserLikedPost(userDao.getIdByUsername(username), likeForm.getPostId()) || !postDao.ifExistsId(likeForm.getPostId()))
            return ResponseEntity.badRequest().body("Вы уже лайкали этот пост или такого поста нет!");
        likeForm.setUserId(userDao.getIdByUsername(username));
        return ResponseEntity.ok(likeDao.createLike(likeForm));
    }
}
