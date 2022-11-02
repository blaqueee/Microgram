package com.example.microgram.Service;

import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.LoginForm;
import com.example.microgram.DTO.UserDto;
import com.example.microgram.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final UserDao userDao;

    @Autowired
    private final PasswordEncoder encoder;

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

    public ResponseEntity<?> login(LoginForm loginForm) {
        var users = userDao.getUserByUsername(loginForm.getUsername());
        if (users.size() == 0)
            return new ResponseEntity<>("Invalid username or password!", HttpStatus.UNAUTHORIZED);
        var user = users.get(0);
        if (!encoder.matches(loginForm.getPassword(), user.getPassword()))
            return new ResponseEntity<>("Invalid username or password!", HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(userDao.getUserDtoByUsername(loginForm.getUsername()).get(0).getId());
    }
}
