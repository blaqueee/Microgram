package com.example.microgram.Service;

import com.example.microgram.DAO.CommentDao;
import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.CommentDto;
import com.example.microgram.DTO.Form.CommentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentPostUserService {
    private final CommentDao commentDao;
    private final PostDao postDao;
    private final UserDao userDao;

    public CommentDto addComment(CommentForm commentForm, Authentication auth) {
        var comment = commentDao.addComment(commentForm);
        comment.setCommentator(userDao.getUserDtoById(commentForm.getUserId()).get());
        return comment;
    }

    public ResponseEntity<String> deleteComment(Long postID, Long commentID, Authentication auth) {
        var username = auth.getName();
        if (!postDao.isPostOwner(postID, username) || !commentDao.ifExistsById(commentID, postID))
            return ResponseEntity.badRequest().body("Комментарий не существует или пост не ваш!");
        return ResponseEntity.ok(commentDao.deleteComment(commentID, postID));
    }
}
