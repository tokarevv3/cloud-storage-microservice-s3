package ru.tokarev.dto.folder;

import lombok.Value;

@Value
public class FolderCreateDto {

    String bucketName;

    String folderName;

    String folderPath;
}
