package com.stream.app.controllers;

import com.stream.app.dtos.VideoMetadataResponseDTO;
import com.stream.app.dtos.request.VideoRequestDTO;
import com.stream.app.services.VideoMetadataService;
import com.stream.app.services.VideoProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/videos")
@CrossOrigin("http://localhost:5173")
public class VideoController {
    private VideoProcessingService videoProcessingService;

    private VideoMetadataService videoMetadataService;

    @Autowired
    public VideoController(VideoProcessingService videoProcessingService, VideoMetadataService videoMetadataService) {
        this.videoProcessingService = videoProcessingService;
        this.videoMetadataService = videoMetadataService;
    }

    @GetMapping
    public ResponseEntity<?> getVideo(@RequestParam("videoId") String videoId) {
        return ResponseEntity
                .ok(videoMetadataService.getVideo(videoId));
    }


    @PostMapping
    public ResponseEntity<?> processVideo(@RequestBody VideoRequestDTO videoRequestDTO) {
        VideoMetadataResponseDTO response = videoProcessingService.processVideo(videoRequestDTO.getVideoId(), videoRequestDTO);

        return ResponseEntity
                .created(URI.create("/api/v1/videos"))
                .body(response);
    }



}
