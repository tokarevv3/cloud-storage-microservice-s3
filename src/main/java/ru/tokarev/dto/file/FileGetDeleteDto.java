package ru.tokarev.dto.file;

import lombok.Value;

@Value
public class FileGetDeleteDto {

    String bucketName;

    String filePath;
}
