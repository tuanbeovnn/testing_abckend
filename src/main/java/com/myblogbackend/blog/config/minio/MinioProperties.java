package com.myblogbackend.blog.config.minio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

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

    public MinioProperties(String accessKey, String pathurlFiles, String pathurlImages, String bucketName, String endpoint, String fileSize, String imageSize, String port, String secretKey, String secure) {
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
