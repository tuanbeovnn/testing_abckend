package com.myblogbackend.blog.repositories;

import com.myblogbackend.blog.models.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository  extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findByCategoryId(Long categoryId, Pageable pageable);
    Page<PostEntity> findByTitleContaining(String title, Pageable pageable);
 }

