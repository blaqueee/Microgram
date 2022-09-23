package com.example.microgram.Service;

import com.example.microgram.DAO.LikeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDao likeDao;

    public String ifUserLiked(String username, int postID) {
        return likeDao.ifUserLiked(username, postID);
    }

    public String addLike(int userID, int postID) {
        // TODO добавления лайка
        // передам айди либо имя пользователя (без разницы)
        // в LikeDao и соответственно добавлю этот лайк
        return "Вы лайкнули этот пост!";
    }

    public String removeLike(int userID, int postID) {
        // TODO удаление лайка
        // передам эти параметры в likeDao
        // а там будет метод, который отправит запрос в БД удалить этот лайк
        return "Вы удалили свой лайк!";
    }
}
