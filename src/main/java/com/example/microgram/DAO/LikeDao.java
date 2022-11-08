package com.example.microgram.DAO;

import com.example.microgram.DTO.Form.LikeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class LikeDao {
    private final JdbcTemplate jdbcTemplate;

    public String ifUserLiked(String username, Long postID) {
        String query = "select count(l.id) from likes l\n" +
                "inner join users u on u.id = l.user_id\n" +
                "where u.username = ? and l.post_id = ?";
        var result = jdbcTemplate.queryForObject(query, Integer.class, new Object[]{username, postID});
        return result != 0 ? "Пользователь '" + username + "' лайкал этот пост!" : "Пользователь " + username + " не лайкал этот пост!";
    }

    public void dropTable() {
        String query = "DROP TABLE IF EXISTS likes";
        jdbcTemplate.execute(query);
        System.out.println("dropped table 'likes'");
    }

    public void createTableLikes() {
        String query = "create table likes\n" +
                "(\n" +
                "    id bigserial primary key not null,\n" +
                "    user_id bigint not null references users (id),\n" +
                "    post_id bigint not null references posts (id),\n" +
                "    time timestamp without time zone not null\n" +
                ");\n";
        jdbcTemplate.update(query);
        System.out.println("created table 'likes'");
    }

    public Long createLike(LikeForm likeForm) {
        LocalDateTime ldt = LocalDateTime.now();
        String query = "INSERT INTO likes(user_id, post_id, time)\n" +
                "VALUES(?, ?, ?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setLong(1, likeForm.getUserId());
            ps.setLong(2, likeForm.getPostId());
            ps.setTimestamp(3, Timestamp.valueOf(ldt));
            return ps;
        }, kh);
        return Objects.requireNonNull(kh.getKey()).longValue();
    }

    public boolean ifUserLikedPost(Long userID, Long postID) {
        String query = "SELECT COUNT(*) FROM likes WHERE user_id = ? AND post_id = ?";
        var count = jdbcTemplate.queryForObject(query, Integer.class, userID, postID);
        return count == 1;
    }
}
