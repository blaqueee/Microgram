package com.example.microgram.Controller;

import com.example.microgram.DTO.UserDto;
import com.example.microgram.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/name/{name}") // поиск пользователя по имени
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

    @GetMapping("/{username}/followers")
    public ResponseEntity<List<UserDto>> getFollowersByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getFollowersByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/{username}/subscriptions")
    public ResponseEntity<List<UserDto>> getSubscriptionsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getSubscriptionsByUsername(username), HttpStatus.OK);
    }
}
