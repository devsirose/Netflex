package com.stream.app.entities;

import com.stream.app.converters.InstantTimeNow;
import com.stream.app.converters.PathConverter;
import com.stream.app.dtos.VideoMetadataResponseDTO;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @Id
    private String videoId;

    private String title;
    private String description;
    @Convert(converter = PathConverter.class)
    private Path sourcePath;
    @Convert(converter = PathConverter.class)
    private Path transcodedPath;
    @Convert(converter = PathConverter.class)
    private List<Path> thumbnails;
    private boolean isReady;
    @Convert(converter = InstantTimeNow.class)
    private Instant processedAt;

    public VideoMetadataResponseDTO toResponseDTO() {

        return VideoMetadataResponseDTO.builder()
                .videoId(videoId)
                .transcodedUrl(transcodedPath == null ? "" : transcodedPath.toString())
                .thumbnailUrls(thumbnails == null ? new ArrayList<>() : thumbnails.stream()
                        .map( t -> t.toString())
                        .toList())
                .processedAt(processedAt)
                .originalFilename(title)
                .build();
    }
}
