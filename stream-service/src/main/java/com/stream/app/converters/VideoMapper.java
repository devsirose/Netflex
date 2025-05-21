package com.stream.app.converters;

import com.stream.app.dtos.request.VideoRequestDTO;
import com.stream.app.entities.Video;

public class VideoMapper {

    public static Video toVideoEntity(VideoRequestDTO videoRequestDTO) {
        return Video.builder().videoId(videoRequestDTO.getVideoId())
                .title(videoRequestDTO.getTitle())
                .description(videoRequestDTO.getDescription())
                .build();
    }
}
