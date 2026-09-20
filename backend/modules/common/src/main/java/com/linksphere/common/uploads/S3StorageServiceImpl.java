package com.linksphere.common.uploads;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class S3StorageServiceImpl implements S3StorageService {

    private final S3Client s3Client;

    private final String bucketName;

    @Override
    public String upload(MultipartFile file, String folder) throws IOException {

        String key = buildKey(file, folder);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        s3Client.putObject(
                request,
                RequestBody.fromBytes(file.getBytes()));

        return getUrl(key);
    }

    @Override
    public List<String> uploadMultiple(List<MultipartFile> files, String folder) throws IOException {
        return files.stream()
                .map(file -> {
                    try {
                        return upload(file, folder);
                    } catch (IOException e) {
                        throw new RuntimeException(
                                "Failed to upload file: " + file.getOriginalFilename(),
                                e);
                    }
                })
                .toList();
    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }

    @Override
    public void deleteMultiple(List<String> keys) {
        keys.forEach(this::delete);
    }

    @Override
    public String getUrl(String key) {
        return "https://" + bucketName +
                ".s3.ap-south-1.amazonaws.com/" + key;
    }

    private String buildKey(
            MultipartFile file,
            String folder) {

        String originalName = file.getOriginalFilename();

        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(
                    originalName.lastIndexOf("."));
        }

        return folder + "/" + UUID.randomUUID() + extension;
    }

}

