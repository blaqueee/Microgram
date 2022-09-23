package com.example.microgram.Service;

import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public List<UserDto> getUserByName(String name) {
       return userDao.getUserByName(name);
    }

    public List<UserDto> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public List<UserDto> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public String isRegistered(String email) {
        return userDao.ifExists(email);
    }

    public List<UserDto> getFollowersByUsername(String username) {
        return userDao.getFollowersByUsername(username);
    }

    public List<UserDto> getSubscriptionsByUsername(String username) {
        return userDao.getSubscriptionsByUsername(username);
    }
}
