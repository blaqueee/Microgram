package com.example.microgram.DAO;

import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.PostUserDto;
import com.example.microgram.Entity.Post;
import com.example.microgram.Utility.DataGenerator.PostExample;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

    public List<PostDto> getPostsByUsername(String username) {
        String query = "select p.id, p.image, p.description, p.time, " +
                "(select count(id)\n" +
                "from comments c where c.post_id = p.id) comments\n" +
                "from posts p\n" +
                "inner join users u on p.user_id = u.id\n" +
                "where u.username = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PostDto.class), username);
    }

    public List<PostDto> getReelsByUsername(String username) {
        String query = "select p.id, p.image, p.description, p.time,\n" +
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
                "    user_id integer not null REFERENCES users (id) \n" +
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

    public PostDto createPost(PostUserDto post) {
        LocalDateTime ld = LocalDateTime.now();
        String query = "INSERT INTO posts(image, description, time, user_id)\n" +
                "VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(query, post.getImage(), post.getDescription(), Timestamp.valueOf(ld), getUserIdByUsername(post.getUsername()));
        return PostDto.builder()
                .image(post.getImage())
                .description(post.getDescription())
                .time(ld)
                .comments(0)
                .build();
    }

    public String deletePost(Long postID, String username) {
        if (!isPostOwner(postID, username))
            return "Вы не можете удалять чужие публикации!";
        deleteCommentsOfPost(postID);
        String query = "DELETE FROM POSTS\n" +
                "WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(query, postID, getUserIdByUsername(username));
        return "Вы успешно удалили публикацию!";
    }

    private boolean isPostOwner(Long postID, String username) {
        String query = "SELECT COUNT(id) FROM POSTS\n" +
                "WHERE id = ? AND user_id = ?;";
        var result = jdbcTemplate.queryForObject(query, Integer.class, postID, getUserIdByUsername(username));
        return result == 1;
    }

    private Long getUserIdByUsername(String username) {
        String query = "SELECT id FROM USERS WHERE USERNAME = ?";
        return jdbcTemplate.queryForObject(query, Long.class, username);
    }

    private void deleteCommentsOfPost(Long postID) {
        String query = "DELETE FROM comments WHERE post_id = ?";
        jdbcTemplate.update(query, postID);
    }

    private void deleteLikesOfPost(Long postID) {
        String query = "DELETE FROM likes WHERE post_id = ?";
        jdbcTemplate.update(query, postID);
    }
}
