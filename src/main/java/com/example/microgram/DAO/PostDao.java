package com.example.microgram.DAO;

import com.example.microgram.DTO.PostDto;
import com.example.microgram.Utility.PostExample;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

    public List<PostDto> getPostsByUsername(String username) {
        String query = "select p.image, p.description, p.time, " +
                "(select count(id)\n" +
                "from comments c where c.post_id = p.id) comments\n" +
                "from posts p\n" +
                "inner join users u on p.user_id = u.id\n" +
                "where u.username = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PostDto.class), username);
    }

    public List<PostDto> getReelsByUsername(String username) {
        String query = "select p.image, p.description, p.time,\n" +
                "(select count(id)\n" +
                "from comments c where c.post_id = p.id) comments\n" +
                "from posts p\n" +
                "inner join subscriptions s on s.user_id = p.user_id\n" +
                "inner join users u on u.id = s.follower_id\n" +
                "where u.username = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PostDto.class), username);
    }

    public void dropTable() {
        String query = "DROP TABLE IF EXISTS posts";
        jdbcTemplate.execute(query);
        System.out.println("dropped table 'posts'");
    }

    public void createTablePosts() {
        String query = "create table posts\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    image text not null,\n" +
                "    description text default 'Отсутствует',\n" +
                "    time timestamp without time zone not null,\n" +
                "    user_id integer not null references users (id)\n" +
                ");\n";
        jdbcTemplate.update(query);
        System.out.println("created table 'posts'");
    }

    public void insertPosts(List<PostExample> posts) {
        String query = "INSERT INTO posts(image, description, time, user_id)\n" +
                "VALUES(?, ?, ?, ?)";
        for (PostExample post : posts) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, post.getImage());
                ps.setString(2, post.getDescription());
                ps.setTimestamp(3, Timestamp.valueOf(post.getTime()));
                ps.setInt(4, post.getUserID());
                return ps;
            });
        }
        System.out.println("inserted " + posts.size() + " rows into 'posts'");
    }
}
