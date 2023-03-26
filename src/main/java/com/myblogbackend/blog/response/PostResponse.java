package com.myblogbackend.blog.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PostResponse {
    private UUID id;
    private String title;
    private String content;

}
