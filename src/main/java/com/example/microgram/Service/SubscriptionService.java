package com.example.microgram.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    public String follow(int followerID, int userID) {
        // TODO логика подписки
        // отправляю также в ДАО
        // и добавляю подписку там
        return "Вы успешно подписались!";
    }

    public String unfollow(int followerID, int userID) {
        // TODO логика отписки
        // отправляю параметры в ДАО
        // и удаляю подписку там
        return "Вы отписались :(";
    }
}
