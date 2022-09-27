package com.example.microgram.DAO;

import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.PostUserImageDto;
import com.example.microgram.Utility.DataGenerator.PostExample;
import com.example.microgram.Utility.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public PostDto createPost(PostUserImageDto post) {
        LocalDateTime ld = LocalDateTime.now();
        String query = "INSERT INTO posts(image, description, time, user_id)\n" +
                "VALUES(?, ?, ?, ?)";
        String fileName = FileUtil.createFileFromMultipartFile(
                post.getImageFile(),
                getAmountOfPostsByUsername(post.getUsername()) + 1,
                post.getUsername()
        );
        jdbcTemplate.update(query, fileName, post.getDescription(), Timestamp.valueOf(ld), getUserIdByUsername(post.getUsername()));

        return PostDto.builder()
                .id(getIdByImage(fileName))
                .image(fileName)
                .description(post.getDescription())
                .time(ld)
                .comments(0)
                .build();
    }

    public String deletePost(Long postID, String username) {
        if (!isPostOwner(postID, username))
            return "Вы не можете удалять чужие публикации!";
        deleteCommentsOfPost(postID);
        deleteLikesOfPost(postID);
        var optImage = getImageById(postID);
        if (optImage.isEmpty())
            return "Не существует такой публикации!";
        FileUtil.deleteImageFile(optImage.get());
        String query = "DELETE FROM POSTS\n" +
                "WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(query, postID, getUserIdByUsername(username));
        return "Вы успешно удалили публикацию!";
    }

    public Integer getAmountOfPostsByUsername(String username) {
        var list = getPostsByUsername(username);
        return list.size();
    }

    public boolean ifExistsId(Long id) {
        String query = "SELECT COUNT(*) FROM posts WHERE id = ?";
        var count = jdbcTemplate.queryForObject(query, Integer.class, id);
        return count == 1;
    }

    public boolean isPostOwner(Long postID, String username) {
        String query = "SELECT COUNT(id) FROM POSTS\n" +
                "WHERE id = ? AND user_id = ?;";
        var result = jdbcTemplate.queryForObject(query, Integer.class, postID, getUserIdByUsername(username));
        return result == 1;
    }

    private Optional<String> getImageById(Long postID) {
        String query = "SELECT image FROM POSTS WHERE id = ?";
        String image = jdbcTemplate.queryForObject(query, String.class, postID);
        return Optional.of(image);
    }

    private Long getIdByImage(String image) {
        String query = "SELECT id FROM posts WHERE image = ?";
        return jdbcTemplate.queryForObject(query, Long.class, image);
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
