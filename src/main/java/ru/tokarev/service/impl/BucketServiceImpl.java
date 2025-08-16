package ru.tokarev.service.impl;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tokarev.service.BucketService;

@Slf4j
@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final MinioClient minioClient;

    @Override
    public boolean createBucket(String bucketName) {
        log.debug("Trying to create bucket '{}'", bucketName);
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            log.debug("Successfully created bucket '{}'", bucketName);
            return true;
        } catch (Exception e) {
            log.warn("Failed to create bucket '{}'", bucketName, e);
            return false;
        }
    }

    @Override
    public void deleteBucket(String bucketName) {
        log.debug("Trying to delete bucket '{}'", bucketName);
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            log.debug("Successfully deleted bucket '{}'", bucketName);
        } catch (Exception e) {
            log.warn("Failed to delete bucket '{}'", bucketName);
        }
    }
}
