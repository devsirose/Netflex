package com.stream.app.services;

import com.stream.app.dtos.VideoMetadataResponseDTO;
import com.stream.app.dtos.request.VideoRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface VideoProcessingService  {
    VideoMetadataResponseDTO processVideo(String videoId, VideoRequestDTO request);
}
