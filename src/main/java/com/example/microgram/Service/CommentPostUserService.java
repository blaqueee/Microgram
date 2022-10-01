package com.example.microgram.Service;

import com.example.microgram.DAO.CommentDao;
import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentPostUserService {
    private final CommentDao commentDao;
    private final PostDao postDao;
    private final UserDao userDao;

    public String addComment(CommentDto commentDto, Authentication auth) {
        var username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "Вы должны авторизоваться, чтобы добавить комментарий!";
        return commentDao.addComment(commentDto, userDao.getIdByUsername(username));
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
