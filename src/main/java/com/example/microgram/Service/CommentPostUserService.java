package com.example.microgram.Service;

import com.example.microgram.DAO.CommentDao;
import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.CommentDto;
import com.example.microgram.Utility.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentPostUserService {
    private final CommentDao commentDao;
    private final PostDao postDao;
    private final UserDao userDao;

    public String addComment(CommentDto commentDto, HttpServletRequest request) {
        var username = CookieUtil.getUsernameFromCookie(request);
        if (username.isEmpty() || !userDao.ifExistsUsername(username.get()))
            return "Вы должны авторизоваться, чтобы добавить комментарий!";
        return commentDao.addComment(commentDto, userDao.getIdByUsername(username.get()));
    }

    public String deleteComment(Long postID, Long commentID, HttpServletRequest request) {
        var username = CookieUtil.getUsernameFromCookie(request);
        if (username.isEmpty() || !userDao.ifExistsUsername(username.get()))
            return "Вы должны авторизоваться, чтобы удалить пост!";
        if (!postDao.isPostOwner(postID, username.get()))
            return "Вы не можете удалять комментарии под чужим постом!";
        return commentDao.deleteComment(commentID);
    }
}
