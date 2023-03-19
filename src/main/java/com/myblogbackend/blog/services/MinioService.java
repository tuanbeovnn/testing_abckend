package com.myblogbackend.blog.services;

import com.myblogbackend.blog.config.minio.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    FileMetadata uploadFile(MultipartFile multipartFile);
}
