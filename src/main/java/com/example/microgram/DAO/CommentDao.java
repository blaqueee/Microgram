package com.example.microgram.DAO;

import com.example.microgram.DTO.CommentDto;
import com.example.microgram.DTO.Form.CommentForm;
import com.example.microgram.Mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentDao {
    private final JdbcTemplate jdbcTemplate;
    private final CommentMapper commentMapper;

    public void dropTable() {
        String query = "DROP TABLE IF EXISTS comments";
        jdbcTemplate.execute(query);
        System.out.println("dropped table 'comments'");
    }

    public void createTableComments() {
        String query = "create table comments\n" +
                "(\n" +
                "    id bigserial primary key not null,\n" +
                "    post_id integer not null references posts (id),\n" +
                "    user_id integer not null references users (id),\n" +
                "    text text not null,\n" +
                "    time timestamp without time zone not null\n" +
                ");\n";
        jdbcTemplate.update(query);
        System.out.println("created table 'comments'");
    }

    public CommentDto addComment(CommentForm commentForm) {
        LocalDateTime ld = LocalDateTime.now();
        String query = "INSERT INTO comments(post_id, user_id, text, time)\n" +
                "VALUES(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setLong(1, commentForm.getPostId());
            ps.setLong(2, commentForm.getUserId());
            ps.setString(3, commentForm.getText());
            ps.setTimestamp(4, Timestamp.valueOf(ld));
            return ps;
        }, keyHolder);
        return commentMapper.toCommentDto(keyHolder, commentForm.getText(), ld);
    }

    public String deleteComment(Long commentID, Long postID) {
        String query = "DELETE FROM comments WHERE id = ?";
        jdbcTemplate.update(query, commentID);
        return "Комментарий успешно удален!";
    }

    public boolean ifExistsById(Long commentID, Long postID) {
        String query = "SELECT COUNT(id) FROM comments WHERE id = ? AND post_id = ?";
        var count = jdbcTemplate.queryForObject(query, Integer.class, commentID, postID);
        return count == 1;
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        String query = "SELECT * FROM comments where post_id = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CommentDto.class), postId);
    }
}