package com.example.microgram.Service;

import com.example.microgram.DAO.CommentDao;
import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDao postDao;
    private final CommentDao commentDao;
    private final UserDao userDao;

    public List<PostDto> getPostsByUsername(String username) {
        var posts = postDao.getPostsByUsername(username);
        return completePosts(posts);
    }

    public List<PostDto> getReelsByUsername(String username) {
        var posts = postDao.getReelsByUsername(username);
        return completePosts(posts);
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
