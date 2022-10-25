package com.example.microgram.Service;

import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.PostForm;
import com.example.microgram.DTO.PostUserImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostUserService {
    private final UserDao userDao;
    private final PostDao postDao;

    public Optional<?> createPost(PostForm postForm, Authentication auth) {
//        var username = auth.getName();
//        if (!userDao.ifExistsUsername(username))
//            return Optional.empty();
        var post = postDao.createPost(postForm);
        post.setPoster(userDao.getUserDtoById(postForm.getUserId()).get());
        return Optional.of(post);
    }

    public String deletePost(Long postID, Authentication auth) {
        var username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "Вы должны авторизоваться, чтобы удалить пост!";
        return postDao.deletePost(postID, username);
    }
}
