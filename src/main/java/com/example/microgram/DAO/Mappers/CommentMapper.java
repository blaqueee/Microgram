package com.example.microgram.DAO.Mappers;

import com.example.microgram.DTO.CommentDto;
import com.example.microgram.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class CommentMapper implements RowMapper<CommentDto> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public CommentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CommentDto.builder()
                .id(rs.getLong(1))
                .text(rs.getString(2))
                .time(rs.getTimestamp(3).toLocalDateTime())
                .commentator(getUserById(rs.getLong(4)))
                .build();
    }

    private UserDto getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDto.class), id);
    }
}
