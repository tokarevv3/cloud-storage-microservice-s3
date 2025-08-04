package ru.tokarev.service;

import java.io.InputStream;

public interface FileService {

    boolean uploadFile(String bucketName, String filePathName, InputStream inputStream, Long size);

    byte[] downloadFile(String bucketName, String filePathName);

    boolean updateFilePath(String bucketName, String oldFilePath, String newFilePath);

    boolean deleteFile(String bucketName, String filePathName);
}
