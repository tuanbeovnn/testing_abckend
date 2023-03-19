package com.myblogbackend.blog.config.minio;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadata implements Serializable {
    private String bucket;
    private String key;
    private String name;
    private String mime;
    private String hash;
    private String etag;
    private Long size;
    private String extension;
    private String url;
    private Boolean publicAccess;
}
