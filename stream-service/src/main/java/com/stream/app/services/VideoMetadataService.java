package com.stream.app.services;

import com.stream.app.dtos.VideoMetadataResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface VideoMetadataService {
    VideoMetadataResponseDTO getVideo(String videoId);

}
