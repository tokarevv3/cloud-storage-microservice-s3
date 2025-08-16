package ru.tokarev.utils;

import lombok.extern.slf4j.Slf4j;
import ru.tokarev.exception.IllegalBucketNameException;

@Slf4j
public class BucketNameCheckUtil {

    private static final String BUCKET_PATTERN = "^bucket-name-\\d+$";

    public static void validateBucketName(String bucketName) {
        if (bucketName == null || !bucketName.matches(BUCKET_PATTERN)) {
            log.error("Invalid bucket name {}", bucketName);
            throw new IllegalBucketNameException("Invalid bucket name: " + bucketName);
        }
    }

}
