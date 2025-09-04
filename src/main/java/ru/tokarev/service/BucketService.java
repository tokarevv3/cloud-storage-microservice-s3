package ru.tokarev.service;

public interface BucketService {

    boolean createBucket(String bucketName);

    void deleteBucket(String bucketName);

}
