package com.example.microgram.Utility;

import com.example.microgram.DAO.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class Generator {

    @Bean
    public String generate(UserDao userDao, PostDao postDao, CommentDao commentDao, LikeDao likeDao, SubscriptionDao subscriptionDao) {
        dropTables(userDao, postDao, commentDao, likeDao, subscriptionDao);
        createTables(userDao, postDao, commentDao, likeDao, subscriptionDao);
        insertTestData(userDao, postDao, commentDao, likeDao, subscriptionDao);
        return "Database has been initialized";
    }

    private void dropTables(UserDao userDao, PostDao postDao, CommentDao commentDao, LikeDao likeDao, SubscriptionDao subscriptionDao) {
        subscriptionDao.dropTable();
        likeDao.dropTable();
        commentDao.dropTable();
        postDao.dropTable();
        userDao.dropTable();
    }

    private void insertTestData(UserDao userDao, PostDao postDao, CommentDao commentDao, LikeDao likeDao, SubscriptionDao subscriptionDao) {
        userDao.insertUsers(generateUsers());
        postDao.insertPosts(generatePosts());
        commentDao.insertComments(generateComments());
        likeDao.insertLikes(generateLikes());
        subscriptionDao.insertSubscriptions(generateSubscriptions());
    }

    private void createTables(UserDao userDao, PostDao postDao, CommentDao commentDao, LikeDao likeDao, SubscriptionDao subscriptionDao) {
        userDao.createTableUsers();
        postDao.createTablePosts();
        commentDao.createTableComments();
        likeDao.createTableLikes();
        subscriptionDao.createTableSubscriptions();
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
}