package com.myblogbackend.blog.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentRequest {
    private String content;
    private UUID postId;
}
