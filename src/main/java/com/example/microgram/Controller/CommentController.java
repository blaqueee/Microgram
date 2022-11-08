package com.example.microgram.Controller;

import com.example.microgram.DTO.CommentDto;
import com.example.microgram.DTO.Form.CommentForm;
import com.example.microgram.Service.CommentService;
import com.example.microgram.Service.CommentPostUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentPostUserService commentPostUserService;

    @PostMapping()
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentForm commentForm, Authentication auth) {
        return new ResponseEntity<>(commentPostUserService.addComment(commentForm, auth), HttpStatus.OK);
    }

    @DeleteMapping("/{postID}/{commentID}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postID, @PathVariable Long commentID, Authentication authentication) {
        return commentPostUserService.deleteComment(postID, commentID, authentication);
    }

}
