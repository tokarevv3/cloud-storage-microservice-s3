package ru.tokarev.api.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tokarev.dto.bucket.BucketDto;
import ru.tokarev.service.BucketService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buckets")
public class BucketController {

    private final BucketService bucketService;

    @PostMapping
    public boolean createBucket(@RequestBody BucketDto bucketDto) {
        return bucketService.createBucket(bucketDto.getBucketName());
    }

    @DeleteMapping
    public void deleteBucket(@RequestBody BucketDto bucketDto) {
        bucketService.deleteBucket(bucketDto.getBucketName());
    }
}
