package ru.tokarev.service.impl;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tokarev.service.FileService;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MinioClient minioClient;

    @Override
    public boolean uploadFile(String bucketName, String filePathName, InputStream inputStream, Long size) {
        log.debug("Trying to upload file [{}] to bucket '{}'", filePathName, bucketName);
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filePathName)
                    .stream(inputStream, size, -1)
                    .build());
            log.debug("Successfully uploaded file {}", filePathName);
            return true;
        } catch (Exception e) {
            log.warn("Failed to upload file [{}]", filePathName, e);
            return false;
        }
    }

    @Override
    public byte[] downloadFile(String bucketName, String filePathName) {
        log.debug("Trying to download file [{}]", filePathName);
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filePathName)
                    .build()).readAllBytes();

        } catch (Exception e) {
            log.warn("Failed to download file [{}]", filePathName, e);
            return null;
        }
    }

    //TODO:: Remake - try-catch for every MinIO`s operation with rollback in catch`s brackets
    @Override
    public boolean updateFilePath(String bucketName, String oldFilePath, String newFilePath) {
        log.debug("Trying to update file [{}] path to [{}] in bucket '{}'", oldFilePath, newFilePath, bucketName);

        try {
            minioClient.copyObject(CopyObjectArgs.builder()
                    .bucket(bucketName)
                    .object(newFilePath)
                    .source(CopySource.builder()
                            .bucket(bucketName)
                            .object(oldFilePath)
                            .build())
                    .build());
            log.debug("Successfully copied [{}] to [{}]", oldFilePath, newFilePath);

            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(oldFilePath)
                    .build());
            log.debug("Successfully updated file path from [{}] to [{}] in bucket '{}'", oldFilePath, newFilePath, bucketName);

            return true;

        } catch (Exception e) {
            log.error("Failed to update file`s path [{}]", oldFilePath, e);
            return false;
        }
    }

    @Override
    public boolean deleteFile(String bucketName, String filePathName) {
        log.debug("Trying to delete file [{}]", filePathName);
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filePathName)
                    .build());
            log.debug("Successfully deleted file [{}]", filePathName);
            return true;
        } catch (Exception e) {
            log.warn("Failed to delete file [{}]", filePathName, e);
            return false;
        }
    }
}
