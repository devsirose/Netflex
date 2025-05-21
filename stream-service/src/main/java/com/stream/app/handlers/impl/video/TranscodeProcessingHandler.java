package com.stream.app.handlers.impl.video;

import com.stream.app.dtos.VideoMetadataResponseDTO;
import com.stream.app.entities.Video;
import com.stream.app.handlers.VideoProcessingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;

@Component
@Slf4j
public class TranscodeProcessingHandler extends VideoProcessingHandler {

    @Value("${files.video.hsl}")
    String HSL_DIR;

    @Value("${files.video.src}")
    String DIR;


    @Override
    public VideoMetadataResponseDTO handle(Video video) {
        video.setSourcePath(Paths.get(buildSourceVideoPath(video.getVideoId())));

        String ffmpegCmd = ffmpegCMDBuilder(video.getVideoId(), video.getSourcePath().toString());
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", ffmpegCmd);
        processBuilder.inheritIO();

        try {
            Process process = processBuilder.start();
            int exit = process.waitFor();
            if (exit != 0) {
                throw new InterruptedException("Cannot kill process");
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error while executing ffmpeg command for videoId: {}, command: {}", video.getVideoId(), ffmpegCmd, e);
            throw new RuntimeException("Failed to execute ffmpeg command for videoId: " + video.getVideoId(), e);
        }

        video.setTranscodedPath(Paths.get(buildTranscodeVideoPath(video.getVideoId())));
        return nextHandle(video);
    }

    private String buildSourceVideoPath (String videoId) {
        return new StringBuilder(DIR)
                .append("/")
                .append(videoId)
                .toString();
    }

    private String buildTranscodeVideoPath (String videoId) {
        return new StringBuilder(HSL_DIR)
                .append("/")
                .append(videoId)
                .toString();
    }

    private String ffmpegCMDBuilder(String videoId, String videoPath) {
        String transcodeVideoPath = buildTranscodeVideoPath(videoId);

        return new StringBuilder()
                .append("ffmpeg -i ")
                .append("\"").append(videoPath).append("\" ")
                .append("-filter_complex ")
                .append("\"[0:v]split=6[v144][v240][v360][v480][v720][v1080];")
                .append("[v144]scale=256:144[v144out];")
                .append("[v240]scale=426:240[v240out];")
                .append("[v360]scale=640:360[v360out];")
                .append("[v480]scale=854:480[v480out];")
                .append("[v720]scale=1280:720[v720out];")
                .append("[v1080]scale=1920:1080[v1080out]\" ")
                // 144p
                .append("-map \"[v144out]\" -map 0:a ")
                .append("-c:v:0 libx264 -b:v:0 300k -c:a:0 aac ")
                .append("-f hls -hls_time 6 -hls_playlist_type vod ")
                .append("-hls_segment_filename \"").append(transcodeVideoPath).append("/144p/segment_%03d.ts\" ")
                .append("\"").append(transcodeVideoPath).append("/144p/index.m3u8\" ")
                // 240p
                .append("-map \"[v240out]\" -map 0:a ")
                .append("-c:v:1 libx264 -b:v:1 500k -c:a:1 aac ")
                .append("-f hls -hls_time 6 -hls_playlist_type vod ")
                .append("-hls_segment_filename \"").append(transcodeVideoPath).append("/240p/segment_%03d.ts\" ")
                .append("\"").append(transcodeVideoPath).append("/240p/index.m3u8\" ")
                // 360p
                .append("-map \"[v360out]\" -map 0:a ")
                .append("-c:v:2 libx264 -b:v:2 800k -c:a:2 aac ")
                .append("-f hls -hls_time 6 -hls_playlist_type vod ")
                .append("-hls_segment_filename \"").append(transcodeVideoPath).append("/360p/segment_%03d.ts\" ")
                .append("\"").append(transcodeVideoPath).append("/360p/index.m3u8\" ")
                // 480p
                .append("-map \"[v480out]\" -map 0:a ")
                .append("-c:v:3 libx264 -b:v:3 1400k -c:a:3 aac ")
                .append("-f hls -hls_time 6 -hls_playlist_type vod ")
                .append("-hls_segment_filename \"").append(transcodeVideoPath).append("/480p/segment_%03d.ts\" ")
                .append("\"").append(transcodeVideoPath).append("/480p/index.m3u8\" ")
                // 720p
                .append("-map \"[v720out]\" -map 0:a ")
                .append("-c:v:4 libx264 -b:v:4 2800k -c:a:4 aac ")
                .append("-f hls -hls_time 6 -hls_playlist_type vod ")
                .append("-hls_segment_filename \"").append(transcodeVideoPath).append("/720p/segment_%03d.ts\" ")
                .append("\"").append(transcodeVideoPath).append("/720p/index.m3u8\" ")
                // 1080p
                .append("-map \"[v1080out]\" -map 0:a ")
                .append("-c:v:5 libx264 -b:v:5 5000k -c:a:5 aac ")
                .append("-f hls -hls_time 6 -hls_playlist_type vod ")
                .append("-hls_segment_filename \"").append(transcodeVideoPath).append("/1080p/segment_%03d.ts\" ")
                .append("\"").append(transcodeVideoPath).append("/1080p/index.m3u8\" ")
                // master playlist
                .append("-master_pl_name ").append(transcodeVideoPath).append("/master.m3u8 ")
                .append("-f hls")
                .toString();
    }
}
