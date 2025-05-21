package com.stream.app.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
@Data
@Builder
public class VideoMetadataResponseDTO {
    private String videoId;
    private String originalFilename;
    private String transcodedUrl;
    private List<String> thumbnailUrls;
    private Instant processedAt;
}
