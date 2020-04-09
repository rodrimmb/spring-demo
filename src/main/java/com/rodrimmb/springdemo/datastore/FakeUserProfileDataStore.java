package com.rodrimmb.springdemo.datastore;

import com.rodrimmb.springdemo.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(UserProfile.builder().id(1L).username("rodri").profileImage(null).build());
        USER_PROFILES.add(UserProfile.builder().id(2L).username("sandra").profileImage(null).build());
        USER_PROFILES.add(UserProfile.builder().id(3L).username("blanca").profileImage(null).build());
        USER_PROFILES.add(UserProfile.builder().id(4L).username("antonio").profileImage(null).build());
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
