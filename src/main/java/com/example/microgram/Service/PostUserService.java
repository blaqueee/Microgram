package com.example.microgram.Service;

import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.PostUserDto;
import com.example.microgram.DTO.PostUserImageDto;
import com.example.microgram.Utility.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostUserService {
    private final UserDao userDao;
    private final PostDao postDao;

    public Optional<?> createPost(MultipartFile image, String description, HttpServletRequest request) {
        var username = CookieUtil.getUsernameFromCookie(request);
        if (username.isEmpty())
            return Optional.empty();
        if (!userDao.ifExistsUsername(username.get()))
            return Optional.empty();

        return Optional.of(postDao.createPost(
                PostUserImageDto.builder()
                        .imageFile(image)
                        .description(description)
                        .username(username.get())
                        .build()
                )
        );
    }

    public String deletePost(Long postID, HttpServletRequest request) {
        var username = CookieUtil.getUsernameFromCookie(request);
        if (username.isEmpty() || !userDao.ifExistsUsername(username.get()))
            return "Вы должны авторизоваться, чтобы удалить пост!";
        return postDao.deletePost(postID, username.get());
    }
}
