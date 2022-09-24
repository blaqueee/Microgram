package com.example.microgram.DAO;

import com.example.microgram.Utility.DataGenerator.CommentExample;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
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
                "    user_id integer not null references posts (id),\n" +
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
}