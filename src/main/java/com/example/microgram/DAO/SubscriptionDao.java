package com.example.microgram.DAO;

import com.example.microgram.Utility.SubscriptionExample;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

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

    public void insertSubscriptions(List<SubscriptionExample> subs) {
        String query = "INSERT INTO subscriptions(user_id, follower_id, time)\n" +
                "VALUES(?, ?, ?)";
        for (SubscriptionExample sub : subs) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, sub.getUserID());
                ps.setInt(2, sub.getFollowerID());
                ps.setTimestamp(3, Timestamp.valueOf(sub.getTime()));
                return ps;
            });
        }
        System.out.println("inserted " + subs.size() + " rows into 'subscriptions'");
    }
}
