package com.example.microgram.Controller;

import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.UserDto;
import com.example.microgram.DataGenerator.Generator;
import com.example.microgram.Service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
@RequiredArgsConstructor
public class MainController {
    private final MainService service;
    private final Generator generator;

    @GetMapping("/")  // генератор данных
    public ResponseEntity<List<String>> insertData() {
        return new ResponseEntity<>(generator.insertTestData(), HttpStatus.OK);
    }

    @GetMapping("/user/{name}") // поиск пользователя по имени
    public ResponseEntity<List<UserDto>> getUserByName(@PathVariable String name) {
        return new ResponseEntity<>(service.getUserByName(name), HttpStatus.OK);
    }

    @GetMapping("/username/{username}") // поиск пользователя по имени пользователя
    public ResponseEntity<List<UserDto>> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/email/{email}") // поиск пользователя по почте
    public ResponseEntity<List<UserDto>> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(service.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/exists/{email}") // проверка на наличие поьзователя в системе
    public ResponseEntity<String> isRegistered(@PathVariable String email) {
        return new ResponseEntity<>(service.isRegistered(email), HttpStatus.OK);
    }

    @GetMapping("/posts/{username}") // увидеть посты пользователя по имени пользователя
    public ResponseEntity<List<PostDto>> getPostsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getPostsByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/reels/{username}") // показать ленту пользователя на основе подписок
    public ResponseEntity<List<PostDto>> getReelsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getReelsByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/liked/{username}/{postID}") // проверка лайка пользователя определенного поста
    public ResponseEntity<String> ifUserLikedThisPost(@PathVariable String username, @PathVariable int postID) {
        return new ResponseEntity<>(service.ifUserLiked(username, postID), HttpStatus.OK);
    }
}
