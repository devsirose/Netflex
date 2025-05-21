package com.stream.app.services;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {
    boolean uploadChunks(int chunkNumber, MultipartFile chunk, String videoId) ;
    boolean mergeChunks(String videoId);
}
