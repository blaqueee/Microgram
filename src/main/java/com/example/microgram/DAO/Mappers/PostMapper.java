package com.example.microgram.DAO.Mappers;

import com.example.microgram.DTO.CommentDto;
import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class PostMapper implements RowMapper<PostDto> {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PostDto.builder()
                .id(rs.getLong(1))
                .image(rs.getString(2))
                .description(rs.getString(3))
                .time(rs.getTimestamp(4).toLocalDateTime())
                .poster(getUserById(rs.getLong(5)) )
                .comments(getCommentsByPostId(rs.getLong(1)))
                .build();
    }

    private List<CommentDto> getCommentsByPostId(Long postId) {
        String sql = "SELECT id, text, time, user_id FROM comments WHERE post_id = ?";
        return jdbcTemplate.query(sql, new CommentMapper(jdbcTemplate), postId);
    }

    private UserDto getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDto.class), id);
    }
}
