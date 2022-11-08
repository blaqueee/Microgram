package com.example.microgram.Controller;

import com.example.microgram.DTO.PostDto;
import com.example.microgram.Mapper.PostMapper;
import com.example.microgram.Service.PostService;
import com.example.microgram.Service.PostUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostUserService postUserService;
    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody MultipartFile image, String description, Long userId, Authentication auth) {
        var post = postUserService.createPost(postMapper.toPostForm(image, description, userId), auth);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postID}")
    public ResponseEntity<?> deletePost(@PathVariable Long postID, Authentication auth) {
        return postUserService.deletePost(postID, auth);
    }

    @GetMapping("/{username}") // увидеть посты пользователя по имени пользователя
    public ResponseEntity<List<PostDto>> getPostsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(postService.getPostsByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/{username}/reels") // показать ленту пользователя на основе подписок
    public ResponseEntity<List<PostDto>> getReelsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(postService.getReelsByUsername(username), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
