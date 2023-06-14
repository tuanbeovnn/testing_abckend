package com.myblogbackend.blog.services.impl;

import com.myblogbackend.blog.exception.commons.BlogRuntimeException;
import com.myblogbackend.blog.exception.commons.ErrorCode;
import com.myblogbackend.blog.models.ViewersEntity;
import com.myblogbackend.blog.repositories.PostRepository;
import com.myblogbackend.blog.repositories.ViewerRepository;
import com.myblogbackend.blog.services.ViewerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViewerServiceImpl implements ViewerService {
    private static final Logger logger = LoggerFactory.getLogger(ViewerServiceImpl.class);
    private final ViewerRepository viewerRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void countUserViewer(final UUID postId) {
        var post = postRepository
                .findById(postId)
                .orElseThrow(() -> new BlogRuntimeException(ErrorCode.ID_NOT_FOUND));
        if (viewerRepository.existsByPostId(postId)) {
            logger.error("Update viewer successfully by id {} ", postId);
            viewerRepository.updateViewer(postId);
            return;
        }
        var viewers = ViewersEntity.builder()
                .viewCounter(1)
                .postId(postId)
                .build();
        logger.error("Create new viewer successfully for postId by id {} ", postId);
        viewerRepository.save(viewers);
    }
}
