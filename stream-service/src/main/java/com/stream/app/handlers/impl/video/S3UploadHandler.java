package com.stream.app.handlers.impl.video;

import com.stream.app.dtos.VideoMetadataResponseDTO;
import com.stream.app.entities.Video;
import com.stream.app.handlers.VideoProcessingHandler;
import org.springframework.stereotype.Component;

@Component
public class S3UploadHandler extends VideoProcessingHandler {

    @Override
    public VideoMetadataResponseDTO handle(Video video) {
        return nextHandle(video);
    }
}
