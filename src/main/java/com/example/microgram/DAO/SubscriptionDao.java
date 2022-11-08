package com.example.microgram.DAO;

import com.example.microgram.DTO.Form.SubscriptionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SubscriptionDao {
    private final JdbcTemplate jdbcTemplate;

    public void dropTable() {
        String query = "DROP TABLE IF EXISTS subscriptions";
        jdbcTemplate.execute(query);
        System.out.println("dropped table 'subscriptions'");
    }

    public void createTableSubscriptions() {
        String query = "create table subscriptions\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    user_id integer not null references users (id),\n" +
                "    follower_id integer not null references users (id),\n" +
                "    time timestamp without time zone not null\n" +
                ")\n";
        jdbcTemplate.update(query);
        System.out.println("created table 'subscriptions'");
    }

    public String follow(SubscriptionForm subscriptionForm) {
        String query = "INSERT INTO subscriptions(user_id, follower_id, time)\n" +
                "VALUES(?, ?, ?)";
        jdbcTemplate.update(query, subscriptionForm.getUserId(), subscriptionForm.getFollowerId(), LocalDateTime.now());
        return "Вы успешно подписались на пользователя!";
    }

    public boolean isFollower(Long userID, Long followerID) {
        String query = "SELECT COUNT(*) FROM subscriptions WHERE user_id = ? AND follower_id = ?";
        var count = jdbcTemplate.queryForObject(query, Integer.class, userID, followerID);
        return count == 1;
    }
}
