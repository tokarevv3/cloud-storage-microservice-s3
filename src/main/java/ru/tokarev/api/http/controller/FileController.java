package ru.tokarev.api.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tokarev.dto.file.FileGetDeleteDto;
import ru.tokarev.dto.file.FileUpdateDto;
import ru.tokarev.dto.file.FileUploadDto;
import ru.tokarev.service.FileService;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping
    public byte[] getFile(@RequestBody FileGetDeleteDto fileGetDeleteDto) {
        return fileService.downloadFile(
                fileGetDeleteDto.getBucketName(),
                fileGetDeleteDto.getFilePath()
        );
    }

    @DeleteMapping
    public boolean deleteFile(@RequestBody FileGetDeleteDto fileGetDeleteDto) {
        return fileService.deleteFile(
                fileGetDeleteDto.getBucketName(),
                fileGetDeleteDto.getFilePath()
        );
    }

    @PostMapping
    public boolean uploadFile(@RequestBody FileUploadDto fileUploadDto) {
        return fileService.uploadFile(
                fileUploadDto.getBucketName(),
                fileUploadDto.getFilePathName(),
                fileUploadDto.getInputStream(),
                fileUploadDto.getSize()
        );
    }

    @PutMapping
    public boolean updateFile(@RequestBody FileUpdateDto fileUpdateDto) {
        return fileService.updateFilePath(
                fileUpdateDto.getBucketName(),
                fileUpdateDto.getOldFilePath(),
                fileUpdateDto.getNewFilePath()
        );
    }


}
