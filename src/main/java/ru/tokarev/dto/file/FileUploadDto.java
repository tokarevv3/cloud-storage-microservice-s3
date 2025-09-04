package ru.tokarev.dto.file;

import lombok.Value;

import java.io.InputStream;

@Value
public class FileUploadDto {

    String bucketName;

    String filePathName;

    InputStream inputStream;

    Long size;
}
