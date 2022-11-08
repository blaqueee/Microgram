package com.example.microgram.Service;

import com.example.microgram.DAO.SubscriptionDao;
import com.example.microgram.DAO.UserDao;
import com.example.microgram.DTO.Form.SubscriptionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SubscriptionUserService {
    private final SubscriptionDao subDao;
    private final UserDao userDao;

    public ResponseEntity<String> follow(SubscriptionForm subForm, Authentication auth) {
        String username = auth.getName();
        if (!userDao.ifExistsId(subForm.getUserId()) || subForm.getUserId().equals(userDao.getIdByUsername(username))
                || subDao.isFollower(subForm.getUserId(), userDao.getIdByUsername(username)))
            return ResponseEntity.badRequest().body("Вы уже подписаны или такого юзера нет!");
        subForm.setFollowerId(userDao.getIdByUsername(username));
        return ResponseEntity.ok(subDao.follow(subForm));
    }
}
