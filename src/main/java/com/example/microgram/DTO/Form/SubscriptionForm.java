package com.example.microgram.DTO.Form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionForm {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("follower_id")
    private Long followerId;
}
