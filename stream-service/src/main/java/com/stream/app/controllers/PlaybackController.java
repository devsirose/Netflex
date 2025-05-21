package com.stream.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/playback")
@CrossOrigin("http://localhost:5173")
public class PlaybackController {

    @Value("${files.video.hsl}")
    private String HSL_DIR;

    @GetMapping("/{videoId}/master.m3u8")
    public ResponseEntity<Resource> serverMasterFile(
            @PathVariable String videoId
    ) {

        Path path = Paths.get(HSL_DIR, videoId, "master.m3u8");


        if (!Files.exists(path)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Resource resource = new FileSystemResource(path);

        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.CONTENT_TYPE, "application/vnd.apple.mpegurl"
                )
                .body(resource);


    }

    @GetMapping("/{videoId}/{quality}/index.m3u8")
    public ResponseEntity<Resource> serveIndexPlaylist(
            @PathVariable String videoId,
            @PathVariable String quality
    ) {
        Path path = Paths.get(HSL_DIR, videoId, quality, "index.m3u8");
        if (!Files.exists(path)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Resource resource = new FileSystemResource(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.apple.mpegurl")
                .body(resource);
    }


    @GetMapping("/{videoId}/{quality}/{segment}.ts")
    public ResponseEntity<Resource> serveSegments(
            @PathVariable String videoId,
            @PathVariable String quality,
            @PathVariable String segment
    ) {
        Path path = Paths.get(HSL_DIR, videoId, quality, segment + ".ts");
        if (!Files.exists(path)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Resource resource = new FileSystemResource(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "video/mp2t")
                .body(resource);
    }

}
