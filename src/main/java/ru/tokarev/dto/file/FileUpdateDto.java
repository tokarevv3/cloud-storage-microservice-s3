package ru.tokarev.dto.file;

import lombok.Value;

@Value
public class FileUpdateDto {

    String bucketName;

    String oldFilePath;

    String newFilePath;
}
