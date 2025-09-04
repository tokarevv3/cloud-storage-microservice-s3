package ru.tokarev.dto.folder;

import lombok.Value;

@Value
public class FolderDeleteDto {

    String bucketName;

    String folderPath;
}
