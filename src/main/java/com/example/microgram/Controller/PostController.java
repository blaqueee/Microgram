package com.example.microgram.Controller;

import com.example.microgram.DTO.PostDto;
import com.example.microgram.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping("/username/{username}") // увидеть посты пользователя по имени пользователя
    public ResponseEntity<List<PostDto>> getPostsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getPostsByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/reels/{username}") // показать ленту пользователя на основе подписок
    public ResponseEntity<List<PostDto>> getReelsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getReelsByUsername(username), HttpStatus.OK);
    }
}
