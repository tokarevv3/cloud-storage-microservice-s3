package ru.tokarev.api.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tokarev.dto.folder.FolderCreateDto;
import ru.tokarev.dto.folder.FolderCreateRootDto;
import ru.tokarev.dto.folder.FolderDeleteDto;
import ru.tokarev.dto.folder.FolderUpdateDto;
import ru.tokarev.service.FolderService;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public boolean createFolder(@RequestBody FolderCreateDto folderCreateDto) {
        return folderService.createFolder(
                folderCreateDto.getBucketName(),
                folderCreateDto.getFolderName(),
                folderCreateDto.getFolderPath()
        );
    }

    @PostMapping("/root")
    public boolean createRootFolder(@RequestBody FolderCreateRootDto folderCreateRootDto) {
        return folderService.createRootFolder(folderCreateRootDto.getBucketName());
    }

    @PutMapping
    public boolean updateFolder(@RequestBody FolderUpdateDto folderUpdateDto) {
        return folderService.updateFolderPath(
                folderUpdateDto.getBucketName(),
                folderUpdateDto.getOldFolderPath(),
                folderUpdateDto.getNewFolderPath()
        );
    }

    @DeleteMapping
    public void deleteFolder(@RequestBody FolderDeleteDto folderDeleteDto) {
        folderService.deleteFolder(
                folderDeleteDto.getBucketName(),
                folderDeleteDto.getFolderPath()
        );
    }
}
