package ru.tokarev.service;

public interface FolderService {

    boolean createFolder(String bucketName, String folderName, String path);

    boolean createRootFolder(String bucketName);

    boolean updateFolderPath(String bucketName, String folderOldPath, String folderNewPath);

    boolean deleteFolder(String bucketName, String folderName);

    void deleteObjectsInFolder(String bucketName, String folderName) throws Exception;

}
