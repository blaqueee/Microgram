package com.example.microgram.Controller;

import com.example.microgram.Service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService service;

    @GetMapping("/{username}/{postID}") // проверка лайка пользователя определенного поста
    public ResponseEntity<String> ifUserLikedThisPost(@PathVariable String username, @PathVariable int postID) {
        return new ResponseEntity<>(service.ifUserLiked(username, postID), HttpStatus.OK);
    }
}
