package com.example.microgram.Controller;

import com.example.microgram.DTO.LikeDto;
import com.example.microgram.Service.LikeService;
import com.example.microgram.Service.LikeUserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final LikeUserPostService likeUserPostService;

    @GetMapping("/{username}/{postID}") // проверка лайка пользователя определенного поста
    public ResponseEntity<String> ifUserLikedThisPost(@PathVariable String username, @PathVariable Long postID) {
        return new ResponseEntity<>(likeService.ifUserLiked(username, postID), HttpStatus.OK);
    }

    @PostMapping
    /*  лайк ставится в виде json
            {
                "post_id": 3
             }
     */
    public ResponseEntity<String> likePost(@RequestBody LikeDto likeDto, HttpServletRequest request) {
        return new ResponseEntity<>(likeUserPostService.likePost(likeDto, request), HttpStatus.OK);
    }
}
