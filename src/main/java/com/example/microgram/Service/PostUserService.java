package com.example.microgram.Service;

import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.PostUserDto;
import com.example.microgram.Entity.Post;
import com.example.microgram.Utility.CookieUtil;
import com.example.microgram.Utility.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostUserService {
    private final UserDao userDao;
    private final PostDao postDao;

    public Optional<PostDto> createPost(PostDto postDto, HttpServletRequest request) {
        var username = CookieUtil.getUsernameFromCookie(request);
        if (username.isEmpty())
            return Optional.empty();
        if (!userDao.ifExistsUsername(username.get()))
            return Optional.empty();
        return Optional.of(postDao.createPost(
                    PostUserDto.builder()
                            .image(postDto.getImage())
                            .description(postDto.getDescription())
                            .username(username.get())
                            .build()
                )
        );
    }
}
