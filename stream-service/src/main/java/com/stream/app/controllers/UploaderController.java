package com.stream.app.controllers;

import com.stream.app.services.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/uploader")
@CrossOrigin("http://localhost:5173")
@Slf4j
public class UploaderController {

    private final StorageService storageService;

    public UploaderController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload-chunks")
    public ResponseEntity<?> upload(
            @RequestParam("videoId")String videoId,
            @RequestParam("chunkNumber") int chunkNumber,
            @RequestParam("chunk") MultipartFile chunk) {

        boolean status = storageService.uploadChunks(chunkNumber, chunk, videoId);
        if (status) {
            return ResponseEntity.ok().build();
        }
        else {
            log.warn("Failed to upload chunk {} for video {}", chunkNumber, videoId);
            return ResponseEntity.badRequest().build();
        }


    }

    @PostMapping("/merge-chunks")
    public ResponseEntity<String> mergeChunks(
            @RequestParam("fileName") String fileName) {

        log.info("Merging chunks for file: {}", fileName);
        boolean status = storageService.mergeChunks(fileName);

        if (status) {
            log.info("Successfully merged chunks into file: {}", fileName);
            return ResponseEntity.ok().build();
        }
        else {
            log.warn("Failed to merge chunks into file: {}", fileName);
            return ResponseEntity.badRequest().build();
        }
    }

}
