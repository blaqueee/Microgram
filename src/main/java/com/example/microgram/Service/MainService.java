package com.example.microgram.Service;

import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.UserDto;
import com.example.microgram.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final UserDao userDao;

    public ResponseEntity<?> createNewUser(User user) {
        if (userDao.ifExists(user))
            return ResponseEntity.badRequest().body("User with this username or email already exists!");
        Long newUserId = userDao.createNewUser(user);
        return ResponseEntity.ok(UserDto.builder()
                .id(newUserId)
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .build());
    }
}
