package ru.tokarev.service.impl;

import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tokarev.service.FolderService;

import java.io.ByteArrayInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final MinioClient minioClient;

    private final String CONFIRMATION = "/confirmation";

    @Override
    public boolean createFolder(String bucketName, String folderName, String path) {
        log.debug("Trying to create folder [{}] with path [{}] in bucket '{}'", folderName, path, bucketName);
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    // Zero bytes object for confirmation folder`s creation
                    .object(path + "/" + folderName + CONFIRMATION)
                    .stream(new ByteArrayInputStream(new byte[0]), 0, 0)
                    .build());
            log.debug("Successfully created folder [{}]", folderName);
            return true;
        } catch (Exception e) {
            log.warn("Failed to create folder [{}]", folderName, e);
            return false;
        }
    }

    @Override
    public boolean createRootFolder(String bucketName) {
        log.debug("Trying to create root folder for bucket '{}'", bucketName);
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object("root-folder" + CONFIRMATION)
                    .stream(new ByteArrayInputStream(new byte[0]), 0, 0)
                    .build());
            log.debug("Successfully created root folder");
            return true;
        } catch (Exception e) {
            log.warn("Failed to create root folder", e);
            return false;
        }
    }

    @Override
    public boolean updateFolderPath(String bucketName, String folderOldPath, String folderNewPath) {
        log.debug("Trying to update folder [{}] path to [{}] in bucket '{}'", folderOldPath, folderNewPath, bucketName);
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(folderOldPath)
                            .recursive(true)
                            .build()
            );
            log.debug("Successfully retrieved objects list for folder [{}]", folderOldPath);

            for (Result<Item> result : results) {
                Item item = result.get();
                String oldObjectName = item.objectName();
                String newObjectName = oldObjectName.replace(folderOldPath, folderNewPath);

                log.debug("Processing object [{}] -> will be moved to [{}]", oldObjectName, newObjectName);

                minioClient.copyObject(
                        CopyObjectArgs.builder()
                                .bucket(bucketName)
                                .object(newObjectName)
                                .source(CopySource.builder()
                                        .bucket(bucketName)
                                        .object(oldObjectName)
                                        .build())
                                .build()
                );
                log.debug("Successfully copied [{}] to [{}]", oldObjectName, newObjectName);

                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(oldObjectName)
                                .build()
                );
                log.debug("Successfully removed old object [{}]", oldObjectName);
            }

            log.debug("Successfully updated folder path from [{}] to [{}] in bucket '{}'", folderOldPath, folderNewPath, bucketName);
            return true;

        } catch (Exception e) {
            log.error("Failed to update folder path from [{}] to [{}] in bucket '{}'. Reason: {}",
                    folderOldPath, folderNewPath, bucketName, e.getMessage(), e);
            throw new RuntimeException("Failed to update folder path: " + e.getMessage(), e);
        }
    }


    @Override
    public boolean deleteFolder(String bucketName, String folderName) {
        log.debug("Trying to delete folder [{}] in bucket '{}'", folderName, bucketName);
        try {
            deleteObjectsInFolder(bucketName, folderName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteObjectsInFolder(String bucketName, String folderName) {
        if (folderName.startsWith("/")) {
            folderName = folderName.substring(1);
        }
        log.debug("Trying to delete objects in folder [{}] in bucket '{}'", folderName, bucketName);

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .recursive(true)
                        .bucket(bucketName)
                        .prefix(folderName)
                        .build()
        );
        log.debug("Successfully retrieved objects list for folder [{}]", folderName);

        int deletedCount = 0;
        for (Result<Item> result : results) {
            String objectName = null;
            try {
                objectName = result.get().objectName();
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .object(objectName)
                                .bucket(bucketName)
                                .build()
                );
                log.debug("Successfully removed object [{}]", objectName);
                deletedCount++;
            } catch (Exception e) {
                log.warn("Failed to delete object [{}] in bucket '{}'. Reason: {}", objectName, bucketName, e.getMessage(), e);
            }
        }

        log.info("Deleted [{}] objects from folder [{}] in bucket '{}'", deletedCount, folderName, bucketName);
    }

}
