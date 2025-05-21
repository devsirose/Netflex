package com.stream.app.dtos.request;

import lombok.Data;

@Data
public class VideoRequestDTO {
    private String videoId;
    private String title;
    private String description;
}
