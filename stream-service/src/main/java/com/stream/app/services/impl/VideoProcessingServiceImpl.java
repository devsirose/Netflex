package com.stream.app.services.impl;

import com.stream.app.converters.VideoMapper;
import com.stream.app.dtos.VideoMetadataResponseDTO;
import com.stream.app.dtos.request.VideoRequestDTO;
import com.stream.app.entities.Video;
import com.stream.app.handlers.VideoProcessingHandler;
import com.stream.app.handlers.impl.video.MetadataProcessingHandler;
import com.stream.app.handlers.impl.video.S3UploadHandler;
import com.stream.app.handlers.impl.video.ThumbnailProcessingHandler;
import com.stream.app.handlers.impl.video.TranscodeProcessingHandler;
import com.stream.app.services.VideoProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoProcessingServiceImpl implements VideoProcessingService {

    private MetadataProcessingHandler metadataProcessingHandler;

    private TranscodeProcessingHandler transcodeProcessingHandler;

    private ThumbnailProcessingHandler thumbnailProcessingHandler;

    private S3UploadHandler s3UploadHandler;

    private VideoProcessingHandler videoProvisor;
    @Autowired
    public VideoProcessingServiceImpl(MetadataProcessingHandler metadataProcessingHandler, TranscodeProcessingHandler transcodeProcessingHandler, ThumbnailProcessingHandler thumbnailProcessingHandler, S3UploadHandler s3UploadHandler) {
        this.metadataProcessingHandler = metadataProcessingHandler;
        this.transcodeProcessingHandler = transcodeProcessingHandler;
        this.thumbnailProcessingHandler = thumbnailProcessingHandler;
        this.s3UploadHandler = s3UploadHandler;

        this.videoProvisor = VideoProcessingHandler.link(
//                this.transcodeProcessingHandler,
                this.thumbnailProcessingHandler,
                this.s3UploadHandler,
                this.metadataProcessingHandler);
    }

    @Override
    public VideoMetadataResponseDTO processVideo(String videoId, VideoRequestDTO videoRequestDTO) {
        Video video = VideoMapper.toVideoEntity(videoRequestDTO);

        return videoProvisor.handle(video);
    }
}
