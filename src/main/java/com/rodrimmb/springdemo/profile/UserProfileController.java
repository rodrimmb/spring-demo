package com.rodrimmb.springdemo.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @PostMapping(
            path = "{id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("id") Long id,
                                       @RequestParam("file") MultipartFile file) {
        userProfileService.uploadUserProfileImage(id, file);
    }

    @GetMapping("{id}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("id") Long id) {
        return userProfileService.downloadUserProfileImage(id);
    }
}
