package com.example.microgram.Service;

import com.example.microgram.DAO.PostDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.PostDto;
import com.example.microgram.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final UserDao userDao;
    private final PostDao postDao;

    public UserDto getUserByName(String name) {
       return userDao.getUserByName(name);
    }

    public UserDto getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public UserDto getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public String isRegistered(String email) {
        return userDao.ifExists(email);
    }

    public List<PostDto> getPostsByUsername(String username) {
        return postDao.getPostsByUsername(username);
    }

    public List<PostDto> getReelsByUsername(String username) {
        return postDao.getReelsByUsername(username);
    }
}
