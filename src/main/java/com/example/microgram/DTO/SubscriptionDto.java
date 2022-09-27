package com.example.microgram.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class SubscriptionDto {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("follower_username")
    private String followerUsername;
}
