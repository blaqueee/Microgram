package com.example.microgram.Service;

import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.Form.PostForm;
import com.example.microgram.DTO.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostUserService {
    private final UserDao userDao;
    private final PostDao postDao;

    public PostDto createPost(PostForm postForm, Authentication auth) {
        var post = postDao.createPost(postForm);
        post.setPoster(userDao.getUserDtoById(postForm.getUserId()).get());
        return post;
    }

    public ResponseEntity<String> deletePost(Long postID, Authentication auth) {
        String username = auth.getName();
        if (!postDao.isPostOwner(postID, username) || postDao.getImageById(postID).isEmpty())
            return new ResponseEntity<>(postDao.deletePost(postID, username), HttpStatus.OK);
        return ResponseEntity.ok(postDao.deletePost(postID, username));
    }
}
