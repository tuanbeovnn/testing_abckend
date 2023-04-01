package com.myblogbackend.blog.request;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PostRequest {
    private String title;
    private String content;
    private UUID categoryId;
}
