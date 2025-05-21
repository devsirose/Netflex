package com.stream.app.services;

import org.springframework.stereotype.Service;

@Service
public interface S3Service {
    void uploadFile(String keyName, String filePath);
    void downloadFile(String keyName, String downloadPath);
}
