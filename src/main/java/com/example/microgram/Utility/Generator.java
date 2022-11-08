package com.example.microgram.Utility;

import com.example.microgram.DAO.*;
import com.example.microgram.DTO.Form.CommentForm;
import com.example.microgram.DTO.Form.LikeForm;
import com.example.microgram.DTO.Form.PostForm;
import com.example.microgram.DTO.Form.SubscriptionForm;
import com.example.microgram.Entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        userDao.dropTableAuthorities();
        userDao.dropTable();
    }

    private void insertTestData(UserDao userDao, PostDao postDao, CommentDao commentDao, LikeDao likeDao, SubscriptionDao subscriptionDao) {
        generateUsers().forEach(userDao::createNewUser);
        generatePosts().forEach(postDao::createPost);
        generateComments().forEach(commentDao::addComment);
        generateLikes().forEach(likeDao::createLike);
        generateSubscriptions().forEach(subscriptionDao::follow);
    }

    private void createTables(UserDao userDao, PostDao postDao, CommentDao commentDao, LikeDao likeDao, SubscriptionDao subscriptionDao) {
        userDao.createTableUsers();
        userDao.createTableAuthorities();
        postDao.createTablePosts();
        commentDao.createTableComments();
        likeDao.createTableLikes();
        subscriptionDao.createTableSubscriptions();
    }

    private List<PostForm> generatePosts() {
        List<PostForm> posts = new ArrayList<>();
        posts.add(new PostForm(1L, FileUtil.toMultipartFile("2901122484.jpg"), "nonsense"));
        posts.add(new PostForm( 2L, FileUtil.toMultipartFile("2901122484.jpg"), "Just me! :D"));
        posts.add(new PostForm(4L, FileUtil.toMultipartFile("2901122484.jpg"), "Just for Fun"));
        posts.add(new PostForm(4L, FileUtil.toMultipartFile("2901122484.jpg"), "Bought a new car"));
        posts.add(new PostForm(5L, FileUtil.toMultipartFile("2901122484.jpg"), "Finally got to Paris"));
        posts.add(new PostForm(5L, FileUtil.toMultipartFile("2901122484.jpg"), "Entered this university"));
        posts.add(new PostForm(2L, FileUtil.toMultipartFile("2901122484.jpg"), "It's pretty fall, isn't it?"));
        return posts;
    }

    private List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().username("blaque").name("Kairat").email("co453289@gmail.com").password("12345678").build());
        users.add(User.builder().username("regina").name("Aikosha").email("reginadellaluna@gmail.com").password("princessofthemoon").build());
        users.add(User.builder().username("carryall").name("Andrey").email("andrey@gmail.com").password("iloveyou").build());
        users.add(User.builder().username("someone").name("Ivan").email("vanyusha@mail.ru").password("12345").build());
        users.add(User.builder().username("kitty").name("Lara").email("larisson@gmail.com").password("kitty_girl").build());
        return users;
    }

    private List<LikeForm> generateLikes() {
        List<LikeForm> likes = new ArrayList<>();
        likes.add(new LikeForm(2L, 4L));
        likes.add(new LikeForm(2L, 5L));
        likes.add(new LikeForm(3L, 6L));
        likes.add(new LikeForm(1L, 4L));
        likes.add(new LikeForm(1L, 6L));
        return likes;
    }

    private List<CommentForm> generateComments() {
        List<CommentForm> comments = new ArrayList<>();
        comments.add(new CommentForm("", 1L, 4L, "WoW!!!!"));
        comments.add(new CommentForm("", 2L, 4L, "Wow! :D"));
        comments.add(new CommentForm("", 1L, 5L, "Looks great!"));
        comments.add(new CommentForm("", 5L, 3L, "He-he!"));
        comments.add(new CommentForm("", 3L, 1L, "What's that!?"));
        return comments;
    }

    private List<SubscriptionForm> generateSubscriptions() {
        List<SubscriptionForm> subs = new ArrayList<>();
        subs.add(new SubscriptionForm(2L, 1L));
        subs.add(new SubscriptionForm(3L, 1L));
        subs.add(new SubscriptionForm(4L, 1L));
        subs.add(new SubscriptionForm(1L, 3L));
        subs.add(new SubscriptionForm(4L, 2L));
        return subs;
    }
}
