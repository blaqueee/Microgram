package com.example.microgram.Service;

import com.example.microgram.DAO.CommentDao;
import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.CommentDto;
import com.example.microgram.DTO.CommentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentPostUserService {
    private final CommentDao commentDao;
    private final PostDao postDao;
    private final UserDao userDao;

    public CommentDto addComment(CommentForm commentForm, Authentication auth) {
//        var username = auth.getName();
//        if (!userDao.ifExistsUsername(username))
//            return "Вы должны авторизоваться, чтобы добавить комментарий!";
        var comment = commentDao.addComment(commentForm);
        comment.setCommentator(userDao.getUserDtoById(commentForm.getUserId()).get());
        return comment;
    }

    public String deleteComment(Long postID, Long commentID, Authentication auth) {
        var username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "Вы должны авторизоваться, чтобы удалить пост!";
        if (!postDao.isPostOwner(postID, username))
            return "Вы не можете удалять комментарии под чужим постом!";
        return commentDao.deleteComment(commentID, postID);
    }
}
