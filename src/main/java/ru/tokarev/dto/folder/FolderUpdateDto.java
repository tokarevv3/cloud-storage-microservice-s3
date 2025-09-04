package ru.tokarev.dto.folder;

import lombok.Value;

@Value
public class FolderUpdateDto {

    String bucketName;

    String oldFolderPath;

    String newFolderPath;
}
