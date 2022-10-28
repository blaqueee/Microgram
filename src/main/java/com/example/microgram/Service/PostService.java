package com.example.microgram.Service;

import com.example.microgram.DAO.PostDao;
import com.example.microgram.DTO.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDao postDao;

    public List<PostDto> getPostsByUsername(String username) {
        return postDao.getPostsByUsername(username);
    }

    public List<PostDto> getReelsByUsername(String username) {
        return postDao.getReelsByUsername(username);
    }

    public List<PostDto> getAllPosts() {
        return postDao.getAllPosts();
    }
}
