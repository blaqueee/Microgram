package com.example.microgram.Controller;

import com.example.microgram.DTO.LikeDto;
import com.example.microgram.Service.LikeService;
import com.example.microgram.Service.LikeUserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final LikeUserPostService likeUserPostService;

    @GetMapping("/{username}/{postID}")
    public ResponseEntity<String> ifUserLikedThisPost(@PathVariable String username, @PathVariable Long postID) {
        return new ResponseEntity<>(likeService.ifUserLiked(username, postID), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> likePost(@RequestBody LikeDto likeDto, Authentication auth) {
        return new ResponseEntity<>(likeUserPostService.likePost(likeDto, auth), HttpStatus.OK);
    }
}
