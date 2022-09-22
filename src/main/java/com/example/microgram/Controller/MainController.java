package com.example.microgram.Controller;

import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.UserDto;
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

    @GetMapping("/user/{name}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String name) {
        return new ResponseEntity<>(service.getUserByName(name), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(service.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/registered/{email}")
    public ResponseEntity<String> isRegistered(@PathVariable String email) {
        return new ResponseEntity<>(service.isRegistered(email), HttpStatus.OK);
    }

    @GetMapping("/posts/{username}")
    public ResponseEntity<List<PostDto>> getPostsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getPostsByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/reels/{username}")
    public ResponseEntity<List<PostDto>> getReelsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getReelsByUsername(username), HttpStatus.OK);
    }
}
