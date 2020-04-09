package com.rodrimmb.springdemo.profile;

import com.rodrimmb.springdemo.bucket.BucketName;
import com.rodrimmb.springdemo.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }


    public void uploadUserProfileImage(Long userProfileId, MultipartFile file) {
        // Do code
        isFileEmpty(file);
        isImage(file);

        UserProfile user = getUser(userProfileId);

        Map<String, String> metadata = extractMetadata(file);

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getId());
        String fileName = String.format("%s-%s", UUID.randomUUID(), file.getOriginalFilename());
        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            user.setProfileImage(fileName);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] downloadUserProfileImage(Long id) {
        UserProfile user = getUser(id);
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getId());

        return user.getProfileImage()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUser(Long userProfileId) {
        return userProfileDataAccessService.getUserProfiles()
                .stream()
                .filter(u -> u.getId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("El ususario no existe"));
    }

    private void isImage(MultipartFile file) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_SVG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("El fichero debe ser una imagen " + file.getContentType());
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()) {
            throw new IllegalStateException("No se puede subir un fichero vacio " + file.getSize());
        }
    }
}
