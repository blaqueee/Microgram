package com.example.microgram.DataGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Generator {
    private final JdbcTemplate jdbcTemplate;

    public List<String> insertTestData() {
        List<String> results = createTables();
        results.add(insertUsers(generateUsers()));
        results.add(insertPosts(generatePosts()));
        results.add(insertComments(generateComments()));
        results.add(insertLikes(generateLikes()));
        results.add(insertSubscriptions(generateSubscriptions()));
        return results;
    }

    private String insertUsers(List<UserEnum> users) {
        String query = "INSERT INTO users(username, name, email, password)\n" +
                "VALUES(?, ?, ?, ?)";
        for (UserEnum user : users) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPassword());
                return ps;
            });
        }
        return "Inserted " + users.size() + " users";
    }

    private String insertPosts(List<PostExample> posts) {
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
        return "Inserted " + posts.size() + " posts";
    }

    private String insertComments(List<CommentExample> comments) {
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
        return "Inserted " + comments.size() + " comments";
    }

    private String insertLikes(List<LikeExample> likes) {
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
        return "Inserted " + likes.size() + " likes";
    }

    private String insertSubscriptions(List<SubscriptionExample> subs) {
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
        return "Inserted " + subs.size() + " subscriptions";
    }

    private List<PostExample> generatePosts() {
        List<PostExample> posts = new ArrayList<>();
        posts.add(new PostExample("anon.jpeg", "nonsense", LocalDateTime.now(), 1));
        posts.add(new PostExample("me.jpeg", "Just me! :D", LocalDateTime.now(), 2));
        posts.add(new PostExample("myface.jpeg", "Just for Fun", LocalDateTime.now(), 4));
        posts.add(new PostExample("car.jpeg", "Bought a new car", LocalDateTime.now(), 4));
        posts.add(new PostExample("paris.jpeg", "Finally got to Paris", LocalDateTime.now(), 5));
        posts.add(new PostExample("university.jpeg", "Entered this university", LocalDateTime.now(), 5));
        posts.add(new PostExample("autumn.jpeg", "It's pretty fall, isn't it?", LocalDateTime.now(), 2));
        return posts;
    }

    private List<UserEnum> generateUsers() {
        List<UserEnum> users = new ArrayList<>(List.of(UserEnum.values()));
        return users;
    }

    private List<LikeExample> generateLikes() {
        List<LikeExample> likes = new ArrayList<>();
        likes.add(new LikeExample(2, 4, LocalDateTime.now()));
        likes.add(new LikeExample(2, 5, LocalDateTime.now()));
        likes.add(new LikeExample(3, 6, LocalDateTime.now()));
        likes.add(new LikeExample(1, 4, LocalDateTime.now()));
        likes.add(new LikeExample(1, 6, LocalDateTime.now()));
        return likes;
    }

    private List<CommentExample> generateComments() {
        List<CommentExample> comments = new ArrayList<>();
        comments.add(new CommentExample(4, 1, "That's amazing!", LocalDateTime.now()));
        comments.add(new CommentExample(4, 2, "Wow! :D", LocalDateTime.now()));
        comments.add(new CommentExample(5, 1, "Looks great!", LocalDateTime.now()));
        comments.add(new CommentExample(3, 5, "He-he!", LocalDateTime.now()));
        comments.add(new CommentExample(1, 3, "What's that!?", LocalDateTime.now()));
        return comments;
    }

    private List<SubscriptionExample> generateSubscriptions() {
        List<SubscriptionExample> subs = new ArrayList<>();
        subs.add(new SubscriptionExample(2, 1, LocalDateTime.now()));
        subs.add(new SubscriptionExample(3, 1, LocalDateTime.now()));
        subs.add(new SubscriptionExample(4, 1, LocalDateTime.now()));
        subs.add(new SubscriptionExample(1, 3, LocalDateTime.now()));
        subs.add(new SubscriptionExample(4, 2, LocalDateTime.now()));
        return subs;
    }

    private List<String> createTables() {
        List<String> results = new ArrayList<>();
        results.add(createTableUsers());
        results.add(createTablePosts());
        results.add(createTableComments());
        results.add(createTableLikes());
        results.add(createTableSubscriptions());
        return results;
    }

    private String createTableUsers() {
        String query = "create table users\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    username varchar(20) not null,\n" +
                "    name varchar(20) not null,\n" +
                "    email varchar(45) not null,\n" +
                "    password varchar(45) not null\n" +
                ");\n";
        jdbcTemplate.update(query);
        return "created table 'users'";
    }

    private String createTablePosts() {
        String query = "create table posts\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    image text not null,\n" +
                "    description text default 'Отсутствует',\n" +
                "    time timestamp without time zone not null,\n" +
                "    user_id integer not null references users (id)\n" +
                ");\n";
        jdbcTemplate.update(query);
        return "created table 'posts'";
    }

    private String createTableComments() {
        String query = "create table comments\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    post_id integer not null references posts (id),\n" +
                "    user_id integer not null references posts (id),\n" +
                "    text text not null,\n" +
                "    time timestamp without time zone not null\n" +
                ");\n";
        jdbcTemplate.update(query);
        return "created table 'comments'";
    }

    private String createTableLikes() {
        String query = "create table likes\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    user_id integer not null references users (id),\n" +
                "    post_id integer not null references posts (id),\n" +
                "    time timestamp without time zone not null\n" +
                ");\n";
        jdbcTemplate.update(query);
        return "created table 'likes'";
    }

    private String createTableSubscriptions() {
        String query = "create table subscriptions\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    user_id integer not null references users (id),\n" +
                "    follower_id integer not null references users (id),\n" +
                "    time timestamp without time zone not null\n" +
                ")\n";
        jdbcTemplate.update(query);
        return "created table 'subscriptions'";
    }
}
