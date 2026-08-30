package com.linksphere.user_service.uploads;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.linksphere.user_service.dto.response.FileUploadResponse;

import io.imagekit.client.ImageKitClient;
import io.imagekit.models.files.FileUploadParams;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageKitService {

    private final ImageKitClient imageKitClient;

    public List<FileUploadResponse> uploadFiles(MultipartFile[] files) {

        return Arrays.stream(files)
                .map(this::uploadFile)
                .toList();
    }

    private FileUploadResponse uploadFile(MultipartFile file) {

        try {

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

            throw new RuntimeException(
                    "Failed to upload file: " + file.getOriginalFilename(),
                    e);
        }
    }
}