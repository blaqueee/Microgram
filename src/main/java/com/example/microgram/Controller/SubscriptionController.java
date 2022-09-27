package com.example.microgram.Controller;

import com.example.microgram.DTO.SubscriptionDto;
import com.example.microgram.Service.SubscriptionService;
import com.example.microgram.Service.SubscriptionUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subService;
    private final SubscriptionUserService subUserService;

    @PostMapping
    /*  подписка отправляется в виде json
        {
            "user_id": 1 -> (id пользователя, на которого хотите подписаться)
        }
     */
    public ResponseEntity<String> follow(@RequestBody SubscriptionDto subDto, HttpServletRequest request) {
        return new ResponseEntity<>(subUserService.follow(subDto, request), HttpStatus.OK);
    }
}
