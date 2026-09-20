package com.linksphere.user_service.uploads;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.linksphere.common.uploads.S3StorageService;
import com.linksphere.user_service.dto.response.FileUploadResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3FileService {

    private final S3StorageService s3StorageService;

    public List<FileUploadResponse> uploadFiles(MultipartFile[] files, String folder) {
        return Arrays.stream(files)
                .map(file -> uploadFile(file, folder))
                .toList();
    }

    public FileUploadResponse uploadFile(MultipartFile file, String folder) {
        try {
            String targetFolder = (folder != null && !folder.isBlank()) ? folder : "uploads";
            String url = s3StorageService.upload(file, targetFolder);

            return FileUploadResponse.builder()
                    .fileId(url)
                    .fileName(file.getOriginalFilename())
                    .url(url)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3: " + file.getOriginalFilename(), e);
        }
    }

    public void deleteFile(String key) {
        s3StorageService.delete(key);
    }

    public void deleteFiles(List<String> keys) {
        s3StorageService.deleteMultiple(keys);
    }
}
