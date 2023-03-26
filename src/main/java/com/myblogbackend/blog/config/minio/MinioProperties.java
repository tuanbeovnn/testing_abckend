package com.myblogbackend.blog.config.minio;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
@Getter
public class MinioProperties {
    private final String accessKey;
    private final String pathurlFiles;
    private final String pathurlImages;
    private final String bucketName;
    private final String endpoint;
    private final String fileSize;
    private final String imageSize;
    private final String port;
    private final String secretKey;
    private final String secure;

    public MinioProperties(final String accessKey, final String pathurlFiles, final String pathurlImages, final String bucketName,
                           final String endpoint, final String fileSize,
                           final String imageSize, final String port, final String secretKey, final String secure) {
        this.accessKey = accessKey;
        this.pathurlFiles = pathurlFiles;
        this.pathurlImages = pathurlImages;
        this.bucketName = bucketName;
        this.endpoint = endpoint;
        this.fileSize = fileSize;
        this.imageSize = imageSize;
        this.port = port;
        this.secretKey = secretKey;
        this.secure = secure;
    }
}
