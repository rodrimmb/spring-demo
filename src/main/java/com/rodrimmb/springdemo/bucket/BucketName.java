package com.rodrimmb.springdemo.bucket;

public enum BucketName {

    PROFILE_IMAGE("springdemo-images");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
