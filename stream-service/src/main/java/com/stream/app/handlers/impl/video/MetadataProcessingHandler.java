package com.stream.app.handlers.impl.video;

import com.stream.app.dtos.VideoMetadataResponseDTO;
import com.stream.app.entities.Video;
import com.stream.app.handlers.VideoProcessingHandler;
import com.stream.app.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetadataProcessingHandler extends VideoProcessingHandler {

    private VideoRepository videoRepository;

    @Autowired
    public MetadataProcessingHandler(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public VideoMetadataResponseDTO handle(Video video) {
        Video newVideo = videoRepository.save(video);
        return nextHandle(newVideo);
    }
}
