package com.example.microgram.DAO;

import com.example.microgram.DTO.CommentDto;
import com.example.microgram.Utility.DataGenerator.CommentExample;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentDao {
    private final JdbcTemplate jdbcTemplate;

    public void dropTable() {
        String query = "DROP TABLE IF EXISTS comments";
        jdbcTemplate.execute(query);
        System.out.println("dropped table 'comments'");
    }

    public void createTableComments() {
        String query = "create table comments\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    post_id integer not null references posts (id),\n" +
                "    user_id integer not null references users (id),\n" +
                "    text text not null,\n" +
                "    time timestamp without time zone not null\n" +
                ");\n";
        jdbcTemplate.update(query);
        System.out.println("created table 'comments'");
    }

    public void insertComments(List<CommentExample> comments) {
        String query = "INSERT INTO comments(post_id, user_id, text, time)\n" +
                "VALUES(?, ?, ?, ?)";
        for (CommentExample comment : comments) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, comment.getPostID());
                ps.setInt(2, comment.getUserID());
                ps.setString(3, comment.getText());
                ps.setTimestamp(4, Timestamp.valueOf(comment.getTime()));
                return ps;
            });
        }
        System.out.println( "inserted " + comments.size() + " rows into 'comments'");
    }

    public String addComment(CommentDto commentDto, Long userID) {
        LocalDateTime ld = LocalDateTime.now();
        String query = "INSERT INTO comments(post_id, user_id, text, time)\n" +
                "VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(query,
                commentDto.getPostID(),
                userID,
                commentDto.getText(),
                ld
        );
        return "Комментарий успешно добавлен!";
    }

    public String deleteComment(Long commentID) {
        if (ifExistsById(commentID)) {
            String query = "DELETE FROM comments WHERE id = ?";
            jdbcTemplate.update(query, commentID);
            return "Комментарий успешно удален!";
        }
        return "Не существующий комментарий!";
    }

    private boolean ifExistsById(Long commentID) {
        String query = "SELECT COUNT(id) FROM comments WHERE id = ?";
        var count = jdbcTemplate.queryForObject(query, Integer.class, commentID);
        return count == 1;
    }
}