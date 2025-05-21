package com.stream.app.services.impl;

import com.stream.app.enums.ErrorKey;
import com.stream.app.exceptions.AppFileCannotCreateException;
import com.stream.app.repositories.VideoRepository;
import com.stream.app.services.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Value("${files.video.src}")
    String DIR;

    @Value("${files.video.hsl}")
    String HSL_DIR;

    @Value("${files.video.chunk}")
    String CHUNK_DIR;

    private VideoRepository videoRepository;


    public StorageServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @PostConstruct
    public void init() {

        File file = new File(DIR);

        try {
            Files.createDirectories(Paths.get(HSL_DIR));
            Files.createDirectories(Paths.get(DIR));
            Files.createDirectories(Paths.get(CHUNK_DIR));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean uploadChunks(int chunkNumber, MultipartFile chunk, String videoId) {

        File chunkFile = new File(chunkPathBuilder(videoId, chunkNumber));

        File parentDir = chunkFile.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new AppFileCannotCreateException("Failed to create directory: " + parentDir.getAbsolutePath(), ErrorKey.FILE_CAN_NOT_CREATE);
        }


        try (OutputStream os = new FileOutputStream(chunkFile)){

            os.write(chunk.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private String chunkPathBuilder(String videoId, int chunkNumber) {
         StringBuilder chunkPathBuilder = new StringBuilder(CHUNK_DIR);
         chunkPathBuilder.append("/")
                 .append(videoId)
                 .append("/")
                 .append(videoId)
                 .append("_part_")
                 .append(chunkNumber);

         return chunkPathBuilder.toString();
    }

    @Override
    public boolean mergeChunks(String videoId) {

        File videoChunkDir = new File(new StringBuilder(CHUNK_DIR)
                        .append("/")
                        .append(videoId)
                        .toString());

        File mergedFile = new File(new StringBuilder(DIR)
                        .append("/")
                        .append(videoId)
                        .toString());

        try (OutputStream os = new FileOutputStream(mergedFile)) {
            File[] chunks = videoChunkDir.listFiles();
            if (chunks == null || chunks.length == 0) return false;

            Arrays.sort(chunks, Comparator.comparingInt(file -> {
                String name = file.getName();
                String part = name.substring(name.lastIndexOf("_part_") + 6);
                return Integer.parseInt(part);
            }));

            for (File chunkFile : chunks) {
                Files.copy(chunkFile.toPath(), os);
                chunkFile.delete();
            }

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
