package com.example.microgram.Service;

public class CommentService {
    public String deleteComment(int commentID) {
        // TODO реализация удаления комментария по ID
        // В CommentDao передам ID, и там будет запрос
        // который удалит запись по переданному ID
        return "Комментарий успешно удален!";
    }

    public String addComment(String text, String time, int postID) {
        // TODO реализация добавления комментария
        // в CommentDao передам эти параметры и там
        //  добавится новая запись в БД
        return "Комментарий успешно добавлен!";
    }

}
