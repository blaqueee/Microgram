package com.example.microgram.Controller;

import com.example.microgram.DTO.LoginForm;
import com.example.microgram.DTO.UserDto;
import com.example.microgram.Entity.User;
import com.example.microgram.Service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final MainService service;

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody User user) {
        return service.createNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        return service.login(loginForm);
    }
}
