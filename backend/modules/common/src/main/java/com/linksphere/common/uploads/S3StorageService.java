package com.linksphere.common.uploads;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3StorageService {

    String upload(MultipartFile file, String folder) throws IOException;

    List<String> uploadMultiple(List<MultipartFile> files, String folder) throws IOException;

    void delete(String key);

    void deleteMultiple(List<String> keys);

    String getUrl(String key);

}
