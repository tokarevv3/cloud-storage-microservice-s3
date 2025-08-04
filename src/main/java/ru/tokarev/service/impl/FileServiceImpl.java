package ru.tokarev.service.impl;

import ru.tokarev.service.FileService;

import java.io.InputStream;

public class FileServiceImpl implements FileService {
    @Override
    public boolean uploadFile(String bucketName, String filePathName, InputStream inputStream, Long size) {
        return false;
    }

    @Override
    public byte[] downloadFile(String bucketName, String filePathName) {
        return new byte[0];
    }

    @Override
    public boolean updateFilePath(String bucketName, String oldFilePath, String newFilePath) {
        return false;
    }

    @Override
    public boolean deleteFile(String bucketName, String filePathName) {
        return false;
    }
}
