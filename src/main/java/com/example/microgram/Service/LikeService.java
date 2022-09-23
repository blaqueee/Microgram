package com.example.microgram.Service;

import com.example.microgram.DAO.LikeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDao likeDao;

    public String ifUserLiked(String username, int postID) {
        return likeDao.ifUserLiked(username, postID);
    }
}
