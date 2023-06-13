package com.myblogbackend.blog.repositories;

import com.myblogbackend.blog.models.PostEntity;
import com.myblogbackend.blog.models.ViewersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViewerRepository extends JpaRepository<ViewersEntity, UUID> {
    Integer countViewCounterByPostId(UUID postId);
}
