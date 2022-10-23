package com.example.microgram.Service;

import com.example.microgram.DAO.CommentDao;
import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.PostUserImageDto;
import com.example.microgram.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostUserService {
    private final UserDao userDao;
    private final CommentDao commentDao;
    private final PostDao postDao;

    public Optional<?> createPost(MultipartFile image, String description, Authentication auth) {
        var username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return Optional.empty();
        var post = postDao.createPost(
                PostUserImageDto.builder()
                        .imageFile(image)
                        .description(description)
                        .username(username)
                        .build()
        );
        return Optional.of(completePosts(List.of(post)).get(0));
    }

    public String deletePost(Long postID, Authentication auth) {
        var username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "Вы должны авторизоваться, чтобы удалить пост!";
        return postDao.deletePost(postID, username);
    }

    private List<PostDto> completePosts(List<PostDto> posts) {
        posts.forEach(e -> {
            var comments = commentDao.getCommentsByPostId(e.getId());
            comments.forEach(c -> {
                var user = userDao.getUserDtoById(c.getId());
                if (user.isEmpty()) {
                    c.setCommentator(UserDto.builder()
                            .id(0L)
                            .name("anonymous")
                            .username("anonymous")
                            .email("anonymous@anon.anon")
                            .build());
                    return;
                }
                c.setCommentator(user.get());
            });
        });
        return posts;
    }
}
