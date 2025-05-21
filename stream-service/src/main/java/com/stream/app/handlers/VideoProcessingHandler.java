package com.stream.app.handlers;

import com.stream.app.dtos.VideoMetadataResponseDTO;
import com.stream.app.entities.Video;

public abstract class VideoProcessingHandler {

    protected VideoProcessingHandler next;


    public static VideoProcessingHandler link(VideoProcessingHandler first, VideoProcessingHandler...  chain) {
        VideoProcessingHandler head = first;
        for (VideoProcessingHandler nextHandler : chain) {
            first.next = nextHandler;
            first = first.next;
        }
        return head;
    }

    public abstract VideoMetadataResponseDTO handle(Video video);
    protected VideoMetadataResponseDTO nextHandle(Video video) {
        if (next == null) {
           return video.toResponseDTO();
        }
        return next.handle(video);
    }
}
