package com.linksphere.user_service.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linksphere.user_service.dto.response.FileUploadResponse;
import com.linksphere.user_service.uploads.S3FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/files/s3")
@RequiredArgsConstructor
public class S3FileController {

    private final S3FileService s3FileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileUploadResponse>> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "folder", defaultValue = "uploads") String folder) {

        List<FileUploadResponse> response = s3FileService.uploadFiles(files, folder);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/upload-single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> uploadSingleFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", defaultValue = "uploads") String folder) {

        FileUploadResponse response = s3FileService.uploadFile(file, folder);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(@RequestParam("key") String key) {
        s3FileService.deleteFile(key);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-multiple")
    public ResponseEntity<Void> deleteMultipleFiles(@RequestBody List<String> keys) {
        s3FileService.deleteFiles(keys);
        return ResponseEntity.noContent().build();
    }
}
