package com.example.microgram.Controller;

import com.example.microgram.DTO.CommentDto;
import com.example.microgram.Service.CommentService;
import com.example.microgram.Service.CommentPostUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentPostUserService commentPostUserService;

    @PostMapping("/{postID}")
    public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto, HttpServletRequest request,
                                             @PathVariable Long postID) {
        commentDto.setPostID(postID);
        return new ResponseEntity<>(commentPostUserService.addComment(commentDto, request), HttpStatus.OK);
    }

    @DeleteMapping("/{postID}/{commentID}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postID, @PathVariable Long commentID, HttpServletRequest request) {
        return new ResponseEntity<>(commentPostUserService.deleteComment(postID, commentID, request), HttpStatus.OK);
    }

}
