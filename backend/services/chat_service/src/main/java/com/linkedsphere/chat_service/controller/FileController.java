package com.linkedsphere.chat_service.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linkedsphere.chat_service.dto.response.FileUploadResponse;
import com.linkedsphere.chat_service.uploads.ImageKitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final ImageKitService imageKitService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileUploadResponse>> uploadFiles(
            @RequestParam("files") MultipartFile[] files) {

        List<FileUploadResponse> response = imageKitService.uploadFiles(files);

        return ResponseEntity.ok(response);
    }
}
