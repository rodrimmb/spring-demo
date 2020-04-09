package com.rodrimmb.springdemo.profile;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class UserProfile {

    private Long id;
    private String username;
    private String profileImage;

    public Optional<String> getProfileImage() {
        return Optional.ofNullable(profileImage);
    }

}
