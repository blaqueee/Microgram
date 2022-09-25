package com.example.microgram.DAO;

import com.example.microgram.Utility.DataGenerator.LikeExample;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;


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
                "    id serial primary key not null,\n" +
                "    user_id integer not null references users (id),\n" +
                "    post_id integer not null references posts (id),\n" +
                "    time timestamp without time zone not null\n" +
                ");\n";
        jdbcTemplate.update(query);
        System.out.println("created table 'likes'");
    }

    public void insertLikes(List<LikeExample> likes) {
        String query = "INSERT INTO likes(user_id, post_id, time)\n" +
                "VALUES(?, ?, ?)";
        for (LikeExample like : likes) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, like.getUserID());
                ps.setInt(2, like.getPostID());
                ps.setTimestamp(3, Timestamp.valueOf(like.getTime()));
                return ps;
            });
        }
        System.out.println("inserted " + likes.size() + " rows into 'likes'");
    }
}
