package com.stream.app.services.impl;

import com.stream.app.dtos.VideoMetadataResponseDTO;
import com.stream.app.entities.Video;
import com.stream.app.enums.ErrorKey;
import com.stream.app.exceptions.AppFileNotFoundException;
import com.stream.app.repositories.VideoRepository;
import com.stream.app.services.VideoMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoMetadataServiceImpl implements VideoMetadataService {

    private VideoRepository videoRepository;

    @Autowired
    public VideoMetadataServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public VideoMetadataResponseDTO getVideo(String videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() ->
                new AppFileNotFoundException("Video not exists", ErrorKey.VIDEO_NOT_FOUND));

        return video.toResponseDTO();
    }
}
