package com.myblogbackend.blog.services;

import java.util.UUID;

public interface ViewerService {
    void countUserViewer(UUID postId);
}
