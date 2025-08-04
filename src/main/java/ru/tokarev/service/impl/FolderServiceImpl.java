package ru.tokarev.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
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
        log.debug("Trying to create folder '{}' with path '{}' in bucket '{}'", folderName, path, bucketName);
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    // Zero bytes object for confirmation folder`s creation
                    .object(path + "/" + folderName + CONFIRMATION)
                    .stream(new ByteArrayInputStream(new byte[0]), 0, 0)
                    .build());
            log.debug("Successfully created folder '{}'", folderName);
            return true;
        } catch (Exception e) {
            log.warn("Failed to create folder '{}'", folderName, e);
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
        return false;
    }

    @Override
    public boolean deleteFolder(String bucketName, String folderName) {
        return false;
    }

    @Override
    public void deleteObjectsInFolder(String bucketName, String FolderName) {

    }
}
