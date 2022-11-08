package com.example.microgram.DAO;

import com.example.microgram.DAO.Mappers.PostMapper;
import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.Form.PostForm;
import com.example.microgram.Utility.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    private final com.example.microgram.Mapper.PostMapper postMapper;

    public List<PostDto> getPostsByUsername(String username) {
        String query = "select p.id, p.image, p.description, p.time, p.user_id " +
                "from posts p\n" +
                "inner join users u on p.user_id = u.id\n" +
                "where u.username = ?";
        return jdbcTemplate.query(query, new PostMapper(jdbcTemplate), username);
    }

    public List<PostDto> getReelsByUsername(String username) {
        String query = "select p.id, p.image, p.description, p.time, p.user_id \n" +
                "from posts p\n" +
                "inner join subscriptions s on s.user_id = p.user_id\n" +
                "inner join users u on u.id = s.follower_id\n" +
                "where u.username = ?";
        return jdbcTemplate.query(query, new PostMapper(jdbcTemplate), username);
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

    public PostDto createPost(PostForm post) {
        LocalDateTime ld = LocalDateTime.now();
        String query = "INSERT INTO posts(image, description, time, user_id)\n" +
                "VALUES(?, ?, ?, ?)";
        String fileName = writePhotoFile(post);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setString(1, fileName);
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(ld));
            ps.setLong(4, post.getUserId());
            return ps;
        }, keyHolder);

        return postMapper.toPostDto(keyHolder, fileName, post.getDescription(), ld);
    }

    public List<PostDto> getAllPosts() {
        String sql = "SELECT p.id, p.image, p.description, p.time, p.user_id\n" +
                "FROM posts p";
        return jdbcTemplate.query(sql, new PostMapper(jdbcTemplate));
    }

    public String deletePost(Long postID, String username) {
        deleteCommentsOfPost(postID);
        deleteLikesOfPost(postID);
        String query = "DELETE FROM POSTS\n" +
                "WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(query, postID, getUserIdByUsername(username));
        return "Вы успешно удалили публикацию!";
    }

    public Integer getAmountOfPostsByUsername(String username) {
        var list = getPostsByUsername(username);
        return list.size();
    }

    public Integer getAmountOfPostsById(Long id) {
        String query = "SELECT COUNT(id) FROM posts where user_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, id);
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

    public Optional<String> getImageById(Long postID) {
        String query = "SELECT image FROM POSTS WHERE id = ?";
        String image = jdbcTemplate.queryForObject(query, String.class, postID);
        return Optional.ofNullable(image);
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

    private String writePhotoFile(PostForm post) {
        return FileUtil.createFileFromMultipartFile(
                post.getFile(),
                getAmountOfPostsById(post.getUserId()) + 1,
                "userID_" + post.getUserId()
        );
    }
}
