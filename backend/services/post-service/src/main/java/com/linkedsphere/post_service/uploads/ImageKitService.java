package com.linkedsphere.post_service.uploads;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.linkedsphere.post_service.dto.response.FileUploadResponse;

import io.imagekit.client.ImageKitClient;
import io.imagekit.models.files.FileUploadParams;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageKitService {

    private final ImageKitClient imageKitClient;
    private final Executor fileUploadExecutor;

    public ImageKitService(
            ImageKitClient imageKitClient,
            @Qualifier("fileUploadExecutor") Executor fileUploadExecutor) {
        this.imageKitClient = imageKitClient;
        this.fileUploadExecutor = fileUploadExecutor;
    }

    public List<FileUploadResponse> uploadFiles(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return List.of();
        }
        return uploadFiles(Arrays.asList(files));
    }

    public List<FileUploadResponse> uploadFiles(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return List.of();
        }

        log.info("Starting parallel upload of {} files", files.size());

        List<CompletableFuture<FileUploadResponse>> futures = files.stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(file -> CompletableFuture.supplyAsync(() -> uploadFile(file), fileUploadExecutor))
                .toList();

        // Wait for all parallel uploads to finish
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    public FileUploadResponse uploadFile(MultipartFile file) {
        try {
            log.info("Uploading file '{}' on thread '{}'", file.getOriginalFilename(), Thread.currentThread().getName());

            FileUploadParams params = FileUploadParams.builder()
                    .file(file.getBytes())
                    .fileName(file.getOriginalFilename())
                    .folder("/linksphere/uploads")
                    .build();

            io.imagekit.models.files.FileUploadResponse response = imageKitClient.files().upload(params);

            return FileUploadResponse.builder()
                    .fileId(response.fileId().orElse(""))
                    .fileName(response.name().orElse(""))
                    .url(response.url().orElse(""))
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

        } catch (IOException e) {
            log.error("Failed to upload file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
        }
    }
}